#!/bin/bash

# Created by sromku with ☕

package=com.sromku.sample.runtests
rawTests=$1
planOutput=$2

if [ -z $rawTests ] || [ -z $planOutput ] ; then
    echo "Missing params"
    exit 1
fi

if [ -e $planOutput ]; then
    rm $planOutput
fi

# [a|b|c|d|e|f|g|h|i|j] - this is array of all available tests
testNames=()

# [a|a|a|b|c|c|c|c|c|d] - this is array of in the same length as of tests
classNames=()

index=-1
while read p; do

    if [[ $p == "$package"* ]] ;
    then
        className="${p//[$'\t\r\n ']}"
        className=${className%:}
        continue
    fi

    if [[ $p == *"test="* ]] ;
    then
        arrIN=(${p//=/ })
        testName=${arrIN[2]}
        testName="${testName//[$'\t\r\n ']}"

        foundDuplicateTest="f"
        for i in "${!testNames[@]}"
        do
            # ooo, we found duplicated test name.. let's start checking if we need this test
            if [ "${testNames[$i]}" == "$testName" ] && [ "${classNames[$i]}" == "$className" ] ; then
                foundDuplicateTest="t"
                break
            fi
        done

        if [ $foundDuplicateTest == "f" ] ; then
            testNames+=("$testName")
            classNames+=("$className")
            index=$((index + 1))
        fi

        continue
    fi

done < "$rawTests"

for i in "${!classNames[@]}"
do

    # print executable test name
    fullTest="${classNames[$i]}#${testNames[$i]}"
    echo "$fullTest" >> $planOutput

done

# total tests
totalTests=${#testNames[@]}
cat $planOutput
echo "TOTAL_TESTS=$totalTests"