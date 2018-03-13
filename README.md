Standalone reproduction of test framework gotcha seen in Scalacheck

https://github.com/rickynils/scalacheck/issues/185

```
sbt:sbt-test-listener> set fork in Test := true

sbt:sbt-test-listener> test
startGroup(sample.SampleTest)
testEvents(event.result = Some(Passed), event.details = ListBuffer())
endGroup(sample.SampleTest)
startGroup(sample.SampleTest-0)
testEvents(event.result = Some(Passed), event.details = ListBuffer(sample.SampleRunner$$anon$3$$anon$4@78e7f531))
endGroup(sample.SampleTest-0)
startGroup(sample.SampleTest-1)
testEvents(event.result = Some(Failed), event.details = ListBuffer(sample.SampleRunner$$anon$3$$anon$4@1fe046b3))
endGroup(sample.SampleTest-1)
startGroup(sample.SampleTest-2)
testEvents(event.result = Some(Passed), event.details = ListBuffer(sample.SampleRunner$$anon$3$$anon$4@6d3f012a))
endGroup(sample.SampleTest-2)
[error] Failed: Total 0, Failed 0, Errors 0, Passed 0
[error] (Test / test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 0 s, completed 13/03/2018 4:48:21 PM
```

```
sbt:sbt-test-listener> set fork in Test := true
[info] Defining Test / fork
[info] The new value will be used by Test / run / runner, Test / run / testGrouping, Test / testGrouping
[info] Reapplying settings...
[info] Set current project to sbt-test-listener (in build file:/Users/jz/code/sbt-test-listener/)
sbt:sbt-test-listener> test
startGroup(sample.SampleTest)
testEvents(event.result = Some(Passed), event.details = WrappedArray())
endGroup(sample.SampleTest)
startGroup(sample.SampleTest)
testEvents(event.result = Some(Passed), event.details = WrappedArray(sbt.ForkMain$ForkEvent@45920e6f))
endGroup(sample.SampleTest)
startGroup(sample.SampleTest)
testEvents(event.result = Some(Failed), event.details = WrappedArray(sbt.ForkMain$ForkEvent@150fc307))
endGroup(sample.SampleTest)
startGroup(sample.SampleTest)
testEvents(event.result = Some(Passed), event.details = WrappedArray(sbt.ForkMain$ForkEvent@7153c723))
endGroup(sample.SampleTest)
[info] Passed: Total 1, Failed 0, Errors 0, Passed 1
[success] Total time: 1 s, completed 13/03/2018 4:48:47 PM
sbt:sbt-test-listener>
```