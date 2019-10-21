import main.AppRouter

import scala.util.{Failure, Success}

import akka.actor.ActorSystem
import akka.http.scaladsl._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import scala.io.StdIn

import slick.jdbc.PostgresProfile.api._

import modules.utils.DbProvider

object WebServer {
  def main(args: Array[String]) {
    lazy val port = 8000
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val db = Database.forConfig("challengerDb")
    DbProvider.setDb(db)

    Http().bindAndHandle(AppRouter.routes, "0.0.0.0", port)
    println(s"Server started at $port ...")
  }
}