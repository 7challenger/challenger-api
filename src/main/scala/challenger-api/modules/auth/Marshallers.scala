package modules.auth.marshallers

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import modules.auth.models.UsersDAO

trait UserFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val loginParamsFormat = jsonFormat2(UsersDAO.LoginParams)

  implicit object userJsonFormat extends RootJsonFormat[UsersDAO.User] {
    def write(u: UsersDAO.User): JsValue = {
      JsObject(
        "id" -> JsNumber(u.id),
        "projectId" -> JsNumber(u.projectId),
        "username" -> JsString(u.username)
      )
    }

    def read(value: JsValue) = {
      value.asJsObject.getFields("id", "projectId", "username", "password") match {
        case Seq(JsNumber(id), JsNumber(projectId), JsString(username), JsString(password)) =>
          new UsersDAO.User(id, projectId, username, password)
        case _ => throw new DeserializationException("UsersDAO.User expected")
      }
    }
  }
}

