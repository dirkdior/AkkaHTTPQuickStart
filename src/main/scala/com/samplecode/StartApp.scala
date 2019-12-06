package com.samplecode

import akka.actor.{Actor, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import com.samplecode.StartApp.{startHttpServer, system}

import scala.util.Failure
import scala.util.Success

object StartApp extends App {

  def startHttpServer(routes: Route, system: ActorSystem): Unit = {
    implicit val classicSystem: akka.actor.ActorSystem = system
    import scala.concurrent.ExecutionContext.Implicits.global

    val futureBinding = Http().bindAndHandle(routes, "localhost", 8080)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate

    }
  }

  val system = ActorSystem("HelloAkkaHttpServer")
  val rootBehaviorActor = system.actorOf(Props[rootBehavior], name = "rootBehaviorActor")
  rootBehaviorActor ! "Start"
}

class rootBehavior extends Actor {

  def receive = {
    case "Start" =>
      system.log.info("rootActor Active")
      val userRegistryActor = context.actorOf(Props(new UserRegistry()), name = "UserRegistry")
      context.watch(userRegistryActor)

      system.log.info("UserRegistryActor Started and Watched")

      val routes = new UserRoutes(context.system, userRegistryActor)

      system.log.info("Called UserRoutes, starting server now")
      startHttpServer(routes.userRoutes, context.system)
  }
}

