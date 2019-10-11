package modules.home

import javax.inject.{Singleton, Inject}

import play.api.routing.sird._
import play.api.routing.SimpleRouter
import play.api.routing.Router.Routes


@Singleton
class HomeRouter @Inject()(controller: HomeController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/hello") => controller.index
  }
}
