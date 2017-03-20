#!/bin/bash

# Created by sromku with ☕

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

# [same length as of tests]
annotations=()

index=-1
while read p; do

    if [[ $p == "com.sromku.sample.runtests"* ]] ;
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

    if [[ $p == *"annotations="* ]] ;
    then
        arrIN=(${p//=/ })
        annotation=${arrIN[2]}
        annotation="${annotation//[$'\t\r\n ']}"
        annotations+=("$annotation")
        continue
    fi

done < "$rawTests"

for i in "${!classNames[@]}"
do
    # flags we can set
    clearData=false
    repeat=0

    # check if we have repeat, clear data and other flags
    annotation=${annotations[$i]}
    anns=(${annotation//,/ })
    for a in "${anns[@]}"
    do
        # check for clear data
        if [ "$a" == "ClearData" ] ; then
            clearData=true
        fi

        # check for number of repeats
        if [[ $a == "Repeat"* ]] ; then
            r=(${a//:/ })
            repeat=${r[1]}
            repeat=$((repeat - 1))

            # just to make sure that we didn't have negative repeat value
            if [ $repeat -lt 0 ]; then
                repeat=0
            fi
        fi

    done

    # by default it will run only once, unless we set repeat for parameterized tests
    for j in $(seq 0 $repeat)
    do

        # if we need to clear data
        if $clearData; then
            echo "clearData" >> $planOutput
        fi

        # print executable test name
        fullTest="${classNames[$i]}#${testNames[$i]}"

        # if we have repeat value, then pass it as well
        if [ $repeat -gt 0 ]; then
            fullTest="$fullTest:$j"
        fi

        # the execution ling
        echo "$fullTest" >> $planOutput

    done

done

# total tests
totalTests=${#testNames[@]}
cat $planOutput
echo "TOTAL_TESTS=$totalTests"