package modules.auth.router

import modules.auth.models.UsersDAO
import modules.auth.controllers.UsersController
import modules.auth.marshallers.UserFormatter

import scala.concurrent.Future
import scala.util.{Success, Failure}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

object AuthRouter {
  val routes: Route = {
    pathPrefix("auth") {
      UsersRouter.routes
    }
  }
}

object UsersRouter extends UserFormatter {
  val routePrefix = "users"

  val routes: Route = {
    concat(
      get {
        pathPrefix(routePrefix / LongNumber) {userId =>
          val futureMaybeUser: Future[UsersDAO.User] = UsersController.getUserById(userId)

          onComplete(futureMaybeUser) {
            case Success(user) => complete(user)
            case Failure(ex) => complete(ex.toString())
          }
        }
      },
      get {
        pathPrefix("authenticate") {
          parameters('username, 'password ) {(username, password) =>
            val futureMaybeUser: Future[UsersDAO.User] =
              UsersController.authenticate(username, password)

            onComplete(futureMaybeUser) {
              case Success(user) => complete(user)
              case Failure(ex) => complete(ex.toString())
            }
          }
        }
      }
    )
  }
}
