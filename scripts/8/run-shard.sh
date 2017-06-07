#!/bin/bash

# prepare devices
devices=()
for word in $(adb devices | grep -v List | grep device)
do
    if [ $word == "device" ]; then
        continue
    fi
    devices+=("$word")
done

# total tests
num=$(grep -c "~~~" artifacts/plan-tests.txt)

# parallel processes as number of devices
threads=${#devices[@]}

# exit if not devices attached
if [ $threads -eq 0 ]; then
    echo "No attached devices"
    return 1
fi

# for script
readonly CONST_NAN_PID=-1

# return 0 (true) if PID is running, otherwise return 1 (false)
isPIDup() {
    if [ $1 -eq $CONST_NAN_PID ]; then
        return 1
    fi

    if ps -p $1 > /dev/null
    then
       return 0
    else
       return 1
    fi
}

buildTestGroupPlan() {

    # params
    groupNum=$1
    selectedDevice=$2
    i=0
    planfile="artifacts/plan-tests-$selectedDevice.txt"
    rm $planfile
    grouping=false
    for line in `cat "artifacts/plan-tests.txt"`
    do

        #
        if [ $line == "~~~" ]; then

            # if already grouping and we reached the ~~~ again, then we need to break and our plan is ready
            if $grouping; then
                break
            fi

            # if we reached the right group of tests, then aggregate them, or move to next group of tests
            if [ $groupNum -eq $i ]; then
                grouping=true
            else
                i=$(( $i + 1 ))
            fi
            continue
        fi

        if $grouping; then
            echo $line >> $planfile
        fi

    done

}


# prepare
pool=()
for ((i=0;i<$threads;i++)); do
    pool[i]=$CONST_NAN_PID
done

# get devices

i=0
while true; do

    thread=-1
    for pi in "${!pool[@]}"
    do
        pid=${pool[$pi]}
        if ! isPIDup $pid; then
            thread=$pi
            break
        fi
    done

    # if all process running and we didn't found idle thread -> check again later
    if [ $thread -eq -1 ]; then
        sleep 0.1
        continue
    fi

    # if we already tested all tests
    if [ $i -eq $num ] ; then
        echo "===== Finished ====="
        break
    fi

    # the idle selected device that will run the next group of tests
    selectedDevice=${devices[$thread]}

    # we build file: "plan-tests-$selectedDevice.txt" that holds tests only for this group
    buildTestGroupPlan $i $selectedDevice

    # run group of tests on selected device
    ./scripts/8/run.sh artifacts/plan-tests-$selectedDevice.txt artifacts $selectedDevice &
    pid=$!
    echo "Running: $i - device: $selectedDevice, thread: $thread, pid: $pid"
    pool[$thread]=$pid

    # increase to next one
    i=$(( $i + 1 ))
done

# looks like we are fine
echo "All Tests PASSED"
echo ""
