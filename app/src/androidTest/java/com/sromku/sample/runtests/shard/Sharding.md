##### Benchmark
```
# run shard 0
adb -s 01e879a2ca701847 shell am instrument -w -e package com.sromku.sample.runtests.shard -e numShards 4 -e shardIndex 0 com.sromku.sample.runtests.test/android.support.test.runner.AndroidJUnitRunner

com.sromku.sample.runtests.shard.ShardA:...
com.sromku.sample.runtests.shard.ShardB:...
com.sromku.sample.runtests.shard.ShardC:..
com.sromku.sample.runtests.shard.ShardD:..

Time: 103.137
OK (10 tests)


# run shard 1
adb -s 01e879a2ca701847 shell am instrument -w -e package com.sromku.sample.runtests.shard -e numShards 4 -e shardIndex 1 com.sromku.sample.runtests.test/android.support.test.runner.AndroidJUnitRunner

com.sromku.sample.runtests.shard.ShardA:..
com.sromku.sample.runtests.shard.ShardB:...
com.sromku.sample.runtests.shard.ShardC:...
com.sromku.sample.runtests.shard.ShardD:..

Time: 13.085
OK (10 tests)


# run shard 2
adb -s 01e879a2ca701847 shell am instrument -w -e package com.sromku.sample.runtests.shard -e numShards 4 -e shardIndex 2 com.sromku.sample.runtests.test/android.support.test.runner.AndroidJUnitRunner

com.sromku.sample.runtests.shard.ShardA:..
com.sromku.sample.runtests.shard.ShardB:..
com.sromku.sample.runtests.shard.ShardC:...
com.sromku.sample.runtests.shard.ShardD:...

Time: 16.125
OK (10 tests)


# run shard 3
adb -s 01e879a2ca701847 shell am instrument -w -e package com.sromku.sample.runtests.shard -e numShards 4 -e shardIndex 3 com.sromku.sample.runtests.test/android.support.test.runner.AndroidJUnitRunner

com.sromku.sample.runtests.shard.ShardA:...
com.sromku.sample.runtests.shard.ShardB:..
com.sromku.sample.runtests.shard.ShardC:..
com.sromku.sample.runtests.shard.ShardD:...

Time: 13.078
OK (10 tests)
```

##### Expected

There is 10 tests of 10 seconds each and 30 tests of 1 second which is total of 130 seconds of tests.
Optimal splitting would be 130/4 seconds for each shard ~ 33 seconds.
This is 67 seconds less than the current implementation because shard #0 took ~ 100 seconds.

`TO BE PROVEN` :wink: