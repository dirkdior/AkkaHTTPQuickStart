package com.samplecode

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{ContentTypes, HttpMethods, HttpRequest, MessageEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import spray.json._

class UserRoutesSpec extends WordSpec with Matchers with ScalatestRouteTest with JsonFormats with ScalaFutures {

  val actorSystem = ActorSystem("UserSystem")
  val userActor = actorSystem.actorOf(Props[UserRegistry], name = "UserRegistryActor")

  lazy val routes = new UserRoutes(actorSystem, userActor).userRoutes

  "UserRoutes" should {
    "return no users if no present (GET /users)" in {
      val request = HttpRequest(uri ="/users")

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        contentType should ===(ContentTypes.`application/json`)

        entityAs[String] should ===("""{"users":[]}""")
      }
    }

    "be able to add users (POST /users" in {
      val user = User("Dayo", 43, "Stuff")
      val userEntity = Marshal(user).to[MessageEntity].futureValue

      val request = Post("/users").withEntity(userEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.Created)

        contentType should ===(ContentTypes.`application/json`)

        entityAs[String] should ===("""{"message":"User Dayo created."}""")
      }
    }

    "be able to remove users (DELETE /users)" in {
      val request = HttpRequest(uri = "/users/Dayo", method = HttpMethods.DELETE)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)

        contentType should ===(ContentTypes.`application/json`)

        //The response should be of type User
        responseAs[String].parseJson.convertTo[User]

        //Or compare the direct string
        //entityAs[String] should ===("""{"message":"User Dayo deleted."}""")
      }
    }
  }
}

