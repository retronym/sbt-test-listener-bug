package sample

import sbt.testing._

object SampleTestFramework {
  val dummyFingerprint: Fingerprint = new AnnotatedFingerprint {
    override def annotationName(): String = "sample.SampleAnnotation"
    override def isModule: Boolean = false
  }
}
class SampleTestFramework extends Framework {

  override def fingerprints(): Array[Fingerprint] = Array(SampleTestFramework.dummyFingerprint)
  override def name(): String = "sample"
  override def runner(args: Array[String], remoteArgs: Array[String], testClassLoader: ClassLoader): Runner = new SampleRunner
}

class SampleRunner extends Runner {
  override def remoteArgs(): Array[String] = Array()
  override def done(): String = ""
  override def args(): Array[String] = Array()
  override def tasks(taskDefs: Array[TaskDef]): Array[Task] = taskDefs.map(rootTask)
  def rootTask(tDef: TaskDef): Task = new Task {
    override def execute(eventHandler: EventHandler, loggers: Array[Logger]): Array[Task] = {
      Array(subTask("test1", true, tDef), subTask("test2", false, tDef), subTask("test3", true, tDef))
    }
    override def tags(): Array[String] = Array()
    override def taskDef(): TaskDef = tDef
  }
  def subTask(name: String, result: Boolean, tDef: TaskDef): Task = {
    val selector = new TestSelector(name)
    val tdef2 = new TaskDef(tDef.fullyQualifiedName(), tDef.fingerprint(), tDef.explicitlySpecified(), Array(selector))
    new Task {
      override def execute(eventHandler: EventHandler, loggers: Array[Logger]): Array[Task] = {
        val event = new Event {
          override def throwable(): OptionalThrowable = new OptionalThrowable
          override def fullyQualifiedName(): String = tdef2.fullyQualifiedName()
          override def duration(): Long = 0L
          override def fingerprint(): Fingerprint = tdef2.fingerprint()
          override def selector(): Selector = tdef2.selectors().head
          override def status(): Status = if (result) Status.Success else Status.Failure
        }
        eventHandler.handle(event)
        Array.empty
      }
      override def tags(): Array[String] = Array()
      override def taskDef(): TaskDef =tdef2
    }
  }
}