package dummy.avg.actor

import akka.actor.{Actor, Props}

object Worker {
  def props = Props(new Worker)
}

class Worker extends Actor {
  import dummy.avg.actor.messages.AvgMessage._

  def receive: Actor.Receive = {
    case Work(list) => sender ! Result(list.sum)
  }
}
