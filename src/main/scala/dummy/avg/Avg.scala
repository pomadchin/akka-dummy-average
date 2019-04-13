package dummy.avg

import dummy.avg.actor.{Listener, Master}
import dummy.avg.actor.messages.AvgMessage.Calculate

import akka.actor.ActorSystem

import scala.util.{Failure, Success}

object Avg {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("AvgSystem")
    import system.dispatcher

    val listener = system.actorOf(Listener.props, "listener")
    // spawn 4 workers, pass the list of numbers to calculate average
    val master = system.actorOf(Master.props(numOfWorkers = 4, (1 to 10000).toList, listener), name = "master")
    master ! Calculate

    // event on termination
    system.whenTerminated onComplete {
      case Success(_) =>
      case Failure(e) => throw e
    }
  }
}
