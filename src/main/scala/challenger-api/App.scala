package main

import modules.auth.{Users}

import scala.util.{Failure, Success}
import scala.concurrent.{Future, Await}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

import slick.jdbc.PostgresProfile.api._

object WebServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher
    val db = Database.forConfig("challengerDb")
    val users = TableQuery[Users]

    val q = for (u <- users) yield u.username
    val res = q.result
    val future: Future[Seq[String]] = db.run(res)

    future.onComplete {
      case Success(s) => println(s"Result: $s")
      case Failure(t) => t.printStackTrace()
    }

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Say hello to Me</h1>"))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  }
}