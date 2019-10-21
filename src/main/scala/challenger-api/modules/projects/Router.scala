package modules.projects.router

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.{HttpEntity, ContentTypes}
import akka.http.scaladsl.server.Directives._

object ProjectsRouter {
  val pathPrefx = "projects"
  val routes: Route = {
    get {
      path(pathPrefx) {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to projects</h1>"))
      }
    }
  }
}
