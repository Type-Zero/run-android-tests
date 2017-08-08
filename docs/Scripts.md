# The flow and script

📗 The blog post: https://medium.com/medisafe-tech-blog/running-android-ui-tests-53e85e5c8da8

<img src="../assets/running_tests.png"/>

### Flow

1. Dump raw file with list of tests and some meta data.
2. Build execution test plan and split by groups.
3. Execute the tests in parallel or not.

<img src="../assets/flow.png"/>

### Script

🔹 **`raw.sh <rawfilepath>`**

```
- rawfilepath = The path to a raw file that will hold all raw tests with meta data.
```

The script doesn’t run the tests. We use `-e log true` flag, we print the tests meta data. The dumped data will look like this:

```
INSTRUMENTATION_STATUS: numtests=5
INSTRUMENTATION_STATUS: stream=
INSTRUMENTATION_STATUS: id=AndroidJUnitRunner
INSTRUMENTATION_STATUS: test=testC
INSTRUMENTATION_STATUS: class=com.sromku.sample.runtests.all.AllOne
INSTRUMENTATION_STATUS: current=3
INSTRUMENTATION_STATUS_CODE: 1
INSTRUMENTATION_STATUS: annotations=Following,Repeat:3,Tags,Test
INSTRUMENTATION_STATUS: tags=sanity,medium
```

If you try to read this unreadable file, you will see the test class and name, annotations and tags. We use it later to build a plan.

🔹 **`plan.sh <rawfilepath> <planfilepath>`**

```
- rawfilepath = The path to the raw file that holds raw tests with meta data.
- planfilepath = The path to the excution plan file that will hold groups of tests with commands.
```

The script iterates over all lines of the raw file, filter and extracts the full name of the tests in format Class#method. It also adds commands like *clearData* and other based on java annotations.

The output of this file will look like:

```
~~~
com.sromku.sample.runtests.all.AllOne#testA
~~~
clearData
com.sromku.sample.runtests.all.AllOne#testB
com.sromku.sample.runtests.all.AllOne#testC:0
com.sromku.sample.runtests.all.AllOne#testC:1
com.sromku.sample.runtests.all.AllOne#testC:2
~~~
com.sromku.sample.runtests.all.AllOne#testD
~~~
com.sromku.sample.runtests.all.AllTwo#testA
TOTAL_TESTS=7
TOTAL_TEST_GROUPS=4
```

You can see the tests split into executable groups of tests, divided by ~~~. This is useful when running in parallel. Check the [com.sromku.sample.runtests.all](https://github.com/medisafe/run-android-tests/tree/master/app/src/androidTest/java/com/sromku/sample/runtests/all) package to see the written tests and used annotations.

🔹 **`run.sh <planfilepath> <outputdir> [device]`**

```
- planfilepath = The path to the excution plan file that will hold groups of tests with commands.
- outputdir = An artifacts folder that will collect all tests outputs like logs, video, dumped db and other files.
- device = *Optional* Specific device identifier that we want to run our tests. If not provided, the first connected device from *adb devices* list will be used.
```

We simply run line by line in <planfilepath> and execute the command or the test.


🔹 **`run-shard.sh <planfilepath> <outputdir>`**

```
- planfilepath = The path to the excution plan file that will hold groups of tests with commands.
- outputdir = An artifacts folder that will collect all tests outputs like logs, video, dumped db and other files.
```

- List all connected devices.
- Create array of connected devices and map to their running processes.
- Loop with delay of 100ms and check if process is closed or running. If process isn't running:
	- Peak executable group of tests (divided by ~~~) and save in *plan-pid-example.txt* 
	- Call on anew process `run.sh plan-pid-example.txt <outputdir> [device]`
	- Map the new created PID to connected [device]


