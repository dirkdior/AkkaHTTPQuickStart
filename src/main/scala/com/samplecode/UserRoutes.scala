package com.samplecode

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.Future
import scala.concurrent.duration._

class UserRoutes(userSystem: ActorSystem, userRegistry: ActorRef) extends JsonFormats {

  implicit val timeout: Timeout = 10.seconds

  def getUsers(): Future[Users] =
    ask(userRegistry, GetUsers).mapTo[Users]

  def getUser(name: String): Future[GetUserResponse] =
    ask(userRegistry, GetUser(name)).mapTo[GetUserResponse]

  def createUser(user: User): Future[ActionPerformed] =
    ask(userRegistry, CreateUser(user)).mapTo[ActionPerformed]

  def deleteUser(name: String): Future[ActionPerformed] =
    ask(userRegistry, DeleteUser(name)).mapTo[ActionPerformed]

  val userRoutes: Route =
    pathPrefix("users") {
      concat(
        pathEnd {
          concat(
            get {
              complete(getUsers())
            },
            post {
              entity(as[User]) { user =>
                onSuccess(createUser(user)) { performed =>
                  complete((StatusCodes.Created, performed))
                }
              }
            })
        },
        path(Segment) { name =>
          concat (
            get {
              rejectEmptyResponse {
                onSuccess(getUser(name)) { response =>
                  complete(response.maybeUser)
                }
              }
            },
            delete {
              onSuccess(deleteUser(name)) { performed =>
                complete((StatusCodes.OK, performed))
              }
            })
        })
    }
}
