package modules.projects.router

import modules.projects.models.ProjectsDAO
import modules.projects.controller.ProjectsConotroller
import modules.projects.marshallers.ProjectFormatter

import modules.utils.ErrorFormatter

import scala.concurrent.Future
import scala.util.{Success, Failure}
import akka.http.scaladsl.marshalling.Marshal

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ContentTypes._
import akka.http.scaladsl.model.headers.`Content-Type`

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

object ProjectsRouter extends ProjectFormatter with ErrorFormatter {
  val getProjectRoute = get {
    path("projects" / LongNumber) {projectId =>
      val futureMaybeProject: Future[ProjectsDAO.Project] =
        ProjectsConotroller.getProjectById(projectId)

      onComplete(futureMaybeProject) {
        case Success(project) => complete(
          StatusCodes.OK,
          List(`Content-Type`(`application/json`)),
          project
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
    getProjectRoute
  }
}
