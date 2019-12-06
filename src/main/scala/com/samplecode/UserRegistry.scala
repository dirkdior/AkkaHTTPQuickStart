package com.samplecode

import akka.actor.Actor

import scala.collection.immutable

case class User(name: String, age: Int, city: String)
case class Users(users: immutable.Seq[User])

sealed trait Command
final case class GetUsers() extends Command
final case class CreateUser(user: User) extends Command
final case class GetUser(name: String) extends Command
final case class DeleteUser(name: String) extends Command

final case class GetUserResponse(maybeUser: Option[User])
final case class ActionPerformed(message: String)

class UserRegistry() extends Actor {
  var users: Set[User] = Set.empty

  def receive = {
    case GetUsers =>
      context.system.log.info("Got a request to GetUsers")
      sender ! Users(users.toSeq)
    case CreateUser(user) =>
      context.system.log.info("Got a request to CreateUser")
      sender ! ActionPerformed(s"User ${user.name} created.")
      users += user
    case GetUser(name) =>
      context.system.log.info("Got a request to Get Single User")
      sender ! GetUserResponse(users.find(_.name == name))
    case DeleteUser(name) =>
      context.system.log.info("Got a request to DeleteUser")
      sender ! ActionPerformed(s"User $name deleted.")
      users = users.filterNot(_.name == name)
  }

}
