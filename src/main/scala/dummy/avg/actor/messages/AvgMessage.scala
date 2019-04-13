package dummy.avg.actor.messages

object AvgMessage {
  sealed trait AvgMessage
  case object Calculate extends AvgMessage
  case class Work(list: Seq[Int]) extends AvgMessage
  case class Result(value: Int) extends AvgMessage
  case class AvgResult(avg: Double, duration: Long) extends AvgMessage
}
