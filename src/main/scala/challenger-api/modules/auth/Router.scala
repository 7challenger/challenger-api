package modules.auth.router

import modules.auth.models.UsersDAO
import modules.auth.controller.UsersController
import modules.auth.marshallers.UserFormatter

import modules.utils.ErrorFormatter

import scala.concurrent.Future
import scala.util.{Success, Failure}
import akka.http.scaladsl.marshalling.Marshal

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.headers.`Content-Type`

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

object AuthRouter {
  val routes: Route = {
    pathPrefix("auth") {
      UsersRouter.routes
    }
  }
}

object UsersRouter extends UserFormatter with ErrorFormatter {
  val routePrefix = "users"

  val authenticateRoute = pathPrefix("authenticate") {
    concat(
      post {
        entity(as[UsersDAO.User]) {loginParams =>
          val futureMaybeUser: Future[UsersDAO.User] =
            UsersController.authenticate(loginParams.username, loginParams.password)

          onComplete(futureMaybeUser) {
            case Success(user) => complete(
              StatusCodes.OK,
              List(`Content-Type`(`application/json`)),
              user
            )

            case Failure(ex) => complete(
              StatusCodes.BadRequest,
              List(`Content-Type`(`application/json`)),
              ex
            )
          }
        }
      },
    )
  }

  val createUserRoute = pathPrefix("create") {
    concat(
      post {
        entity(as[UsersDAO.User]) {newUser =>
          val futureMaybeUser: Future[UsersDAO.User] =
            UsersController.create(newUser.username, newUser.password)

          onComplete(futureMaybeUser) {
            case Success(user) => complete(
              StatusCodes.OK,
              List(`Content-Type`(`application/json`)),
              user
            )

            case Failure(ex) => complete(
              StatusCodes.BadRequest,
              List(`Content-Type`(`application/json`)),
              ex
            )
          }
        }
      },
    )
  }

  val getUserRoute = get {
    pathPrefix(routePrefix / LongNumber) {userId =>
      val futureMaybeUser: Future[UsersDAO.User] =
        UsersController.getUserById(userId)

      onComplete(futureMaybeUser) {
        case Success(user) => complete(
          StatusCodes.OK,
          List(`Content-Type`(`application/json`)),
          user
        )

        case Failure(ex) => complete(
          StatusCodes.BadRequest,
          List(`Content-Type`(`application/json`)),
          ex
        )
      }
    }
  }

  val routes: Route = {
    concat(
      getUserRoute,
      createUserRoute,
      authenticateRoute
    )
  }
}
