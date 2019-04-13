package dummy.avg.actor

import akka.actor.{Actor, Props}

object Listener {
  def props = Props(new Listener)
}

class Listener extends Actor {
  import dummy.avg.actor.messages.AvgMessage._

  def receive: Actor.Receive = {
    case AvgResult(sum, duration) =>
      println(s"\n\tAvg result: \t\t${sum}\n\tCalculation time: \t${duration} ms")
      // async termination
      context.system.terminate
  }
}
