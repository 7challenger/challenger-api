package main

import modules.auth.router.AuthRouter
import modules.projects.router.ProjectsRouter
import akka.http.scaladsl.server.Directives._

object AppRouter {
  val routes = AuthRouter.routes ~ ProjectsRouter.routes
}
