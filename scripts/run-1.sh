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

runningTest="$2/running-test.txt"

for line in `cat "$planFile"`
do

    # print full test name
    echo "$line"

    # run test
    adb shell am instrument -w -e class $line com.sromku.sample.runtests.test/android.support.test.runner.AndroidJUnitRunner > $runningTest

    # SCAN for errors
    shortReason=''
    if grep -q "FAILURES!!!" $runningTest; then
        shortReason=$(head -5 "$runningTest")
        shortReason=${shortReason//[$'\t\r\n ' / ]}
        shortReason=${shortReason//[$'\t\r\n']}
    fi

    # if 'shortReason' is not empty, then we found a bug!
    if [ ! -z "$shortReason" ] ; then

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
