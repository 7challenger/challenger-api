package app

import javax.inject.{Singleton, Inject}

import play.api.routing.SimpleRouter
import play.api.routing.Router.Routes

import modules.home.HomeRouter


@Singleton
class AppRouter @Inject()(homeRouter: HomeRouter) extends SimpleRouter {
  override def routes: Routes = homeRouter.routes
}
