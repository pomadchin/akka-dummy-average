package dummy.avg.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.BalancingPool

object Master {
  def props(numOfWorkers: Int, list: Seq[Int], listener: ActorRef, numOfSlices: Option[Int] = None) =
    Props(new Master(numOfWorkers, list, listener, numOfSlices.getOrElse(list.length / numOfWorkers + 1)))
}

class Master(numOfWorkers: Int, list: Seq[Int], listener: ActorRef, numOfSlices: Int) extends Actor {
  import dummy.avg.actor.messages.AvgMessage._

  val start: Long = System.currentTimeMillis
  var sum: Long = 0
  var numOfResults: Int = 0
  var numOfMessages: Int = 0

  val workerRouter: ActorRef = context.actorOf(Props[Worker].withRouter(BalancingPool(numOfWorkers)), name = "workerRouter")

  def receive: Actor.Receive = {
    case Calculate =>
      list.grouped(numOfSlices).foreach { l => workerRouter ! Work(l); numOfMessages += 1 }

    case Result(value) =>
      sum += value
      numOfResults += 1

      if (numOfResults == numOfMessages) {
        listener ! AvgResult(sum.toDouble / list.length, duration = System.currentTimeMillis - start)
        // Stops this actor and all its supervised children
        context.stop(self)
      }
  }
}
