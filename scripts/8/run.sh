#!/bin/bash

# Created by sromku with ☕
package=com.sromku.sample.runtests
planFile=$1
outputDir=$2
device=$3

# check for correct input
if [ -z $planFile ] || [ -z $outputDir ] ; then
    echo "Missing params"
    exit 1
fi

## check for connected devices
#if [ -z "$(adb devices | grep -v List | grep device)" ] ; then
#    echo "No connected devices"
#    exit 1
#fi

# clear notifications
clearNotifications() {
    adb -s $1 shell input keyevent 3
    adb -s $1 shell input swipe 0 0 0 300
    num=$(adb -s "$1" shell dumpsys notification | grep NotificationRecord | wc -l)
    while [ $num -gt 0 ]; do
        adb -s $1 shell input swipe 0 400 300 400
        num=$(( $num - 1 ))
    done
    adb -s $1 shell input keyevent 3
}

runningTest="$outputDir/running-test-$device.txt"
recording="recording-$device.mp4"
logcat="$outputDir/logcat-$device.txt"

for line in `cat "$planFile"`
do

    #
    if [ $line == "~~~" ]; then
        continue
    fi

    # print full test name
    echo "$line"

    # in case of clear data we execute and move to next line
    if [ $line == "clearData" ]; then
        adb -s $device shell pm clear $package
        sleep 3
        echo ""
        continue
    fi

    # in case of clear notifications we execute and move to next line
    if [ $line == "clearNotifications" ]; then
        clearNotifications $device
        sleep 3
        echo ""
        continue
    fi

    # start collecting logs
    adb -s $device logcat > "$logcat" &
    PID_LOGCAT=$!

    # start recording video
    adb -s $device shell screenrecord --bit-rate 6000000 "/sdcard/$recording" &
    PID_RECORDING=$!

    # parse to get param (in case of params)
    if [[ $line == *":"* ]]; then
        # attach index param
        testArr=(${line//:/ })
        test=${testArr[0]}
        index=${testArr[1]}
        adb -s $device shell am instrument -w -e class $test -e paramIndex $index $package.test/android.support.test.runner.AndroidJUnitRunner > $runningTest
    else
        # run as usual
        adb -s $device shell am instrument -w -e class $line $package.test/android.support.test.runner.AndroidJUnitRunner > $runningTest
    fi

    # kill logcat process
    kill $PID_LOGCAT
    sleep 1

    # kill recording process
    kill $PID_RECORDING
    sleep 3

     # pull and remove recording from device
    adb -s $device pull "/sdcard/$recording" $outputDir/$recording
    adb -s $device shell rm "/sdcard/$recording"

    # SCAN for errors
    shortReason=''
    if grep -q "FAILURES!!!" $runningTest; then
        shortReason=$(head -5 "$runningTest")
        shortReason=${shortReason//[$'\t\r\n ' / ]}
        shortReason=${shortReason//[$'\t\r\n']}
    fi

    # if 'shortReason' is not empty, then we found a bug!
    if [ ! -z "$shortReason" ] ; then

        # dump db
        adb -s $device shell "run-as $package cat /data/data/$package/databases/app.db" > artifacts/app-$device.db

        # extract preferences
        adb -s $device shell "run-as $package cat /data/data/$package/shared_prefs/'$package'_preferences.xml" > artifacts/shared_preferences-$device.xml

        # dump netstats, battery, alarms
        adb -s $device shell dumpsys netstats > artifacts/dumpsys_netstats-$device.txt
        adb -s $device shell dumpsys batterystats > artifacts/dumpsys_batterystats-$device.txt
        adb -s $device shell dumpsys alarm > artifacts/dumpsys_alarm-$device.txt

        # exit on fail
        echo "[x] FAIL"
        echo "$shortReason"
        exit 1

    fi

    echo "[v] OK"
    echo ""

done

# clean leftovers since we are fine
if [ -e $runningTest ]; then
    rm $runningTest
fi

# looks like we are fine
echo ""
