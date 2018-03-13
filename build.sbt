libraryDependencies += "org.scala-sbt" % "test-interface" % "1.0"

testListeners += new TestsListener {
  override def doInit(): Unit = ()
  override def doComplete(finalResult: TestResult): Unit = ()
  override def endGroup(name: String, t: Throwable): Unit = println(s"endGroup($name)")
  override def endGroup(name: String, result: TestResult): Unit = println(s"endGroup($name)")
  override def testEvent(event: TestEvent): Unit = println(s"testEvents(event.result = ${event.result}, event.details = ${event.detail})")
  override def startGroup(name: String): Unit = println(s"startGroup($name)")
}

parallelExecution in ThisBuild := false

testFrameworks += TestFramework("sample.SampleTestFramework")
