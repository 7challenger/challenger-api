package app

import play.api.{ApplicationLoader, BuiltInComponentsFromContext}
import play.api.ApplicationLoader.Context
import play.api.routing.Router
import play.filters.HttpFiltersComponents

import app.AppRouter
import modules.home.{HomeRouter, HomeController}


class ChallengerLoader extends ApplicationLoader {
  def load(context: Context) = {
    new ChallengerComponents(context).application
  }
}

class ChallengerComponents(context: Context)
    extends BuiltInComponentsFromContext(context)
    with HttpFiltersComponents {

  lazy val homeController = new HomeController(controllerComponents)
  lazy val homeRouter = new HomeRouter(homeController)

  lazy val router = new AppRouter(homeRouter)
}
