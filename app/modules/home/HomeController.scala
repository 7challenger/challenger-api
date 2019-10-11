package modules.home

import javax.inject.{Singleton, Inject}

import play.api.mvc.{ControllerComponents, AbstractController}


@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index = Action {
    Ok("Hello world")
  }
}