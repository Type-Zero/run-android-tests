#!/bin/bash

# Created by sromku with ☕

planFile=$1
outputDir=$2

# check for correct input
if [ -z $planFile ] || [ -z $outputDir ] ; then
    echo "Missing params"
    exit 1
fi

# check for connected devices
if [ -z "$(adb devices | grep -v List | grep device)" ] ; then
    echo "No connected devices"
    exit 1
fi

runningTest="$outputDir/running-test.txt"
recording="recording.mp4"
logcat="$outputDir/logcat.txt"

for line in `cat "$planFile"`
do

    # print full test name
    echo "$line"

    # in case of clear data we execute and move to next line
    if [ $line == "clearData" ]; then
        adb shell pm clear com.sromku.sample.runtests
        sleep 3
        echo ""
        continue
    fi

    # start collecting logs
    adb logcat > "$logcat" &
    PID_LOGCAT=$!

    # start recording video
    adb shell screenrecord --bit-rate 6000000 "/sdcard/$recording" &
    PID_RECORDING=$!

    # parse to get param (in case of params)
    if [[ $line == *":"* ]]; then
        # attach index param
        testArr=(${line//:/ })
        test=${testArr[0]}
        index=${testArr[1]}
        adb shell am instrument -w -e class $test -e paramIndex $index com.sromku.sample.runtests.test/android.support.test.runner.AndroidJUnitRunner > $runningTest
    else
        # run as usual
        adb shell am instrument -w -e class $line com.sromku.sample.runtests.test/android.support.test.runner.AndroidJUnitRunner > $runningTest
    fi

    # kill logcat process
    kill $PID_LOGCAT
    sleep 1

    # kill recording process
    kill $PID_RECORDING
    sleep 3

     # pull and remove recording from device
    adb pull "/sdcard/$recording" $outputDir/$recording
    adb shell rm "/sdcard/$recording"

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
        adb shell "run-as com.sromku.sample.runtests cat /data/data/com.sromku.sample.runtests/databases/app.db" > artifacts/app.db

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
echo "All Tests PASSED"
echo ""
