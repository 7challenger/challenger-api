package modules.auth.marshallers

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import modules.auth.models.UsersDAO

trait UserFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  // TODO: move to generic type smth like Option[T] => JsOption[T]
  def valueOrNull(value: Option[Long]) = {
    value match {
      case None => JsNull
      case Some(id) => JsNumber(id)
    }
  }

  implicit val loginParamsFormat = jsonFormat2(UsersDAO.LoginParams)

  implicit object userJsonFormat extends RootJsonFormat[UsersDAO.User] {
    def write(u: UsersDAO.User): JsValue = {
      JsObject(
        "id" -> valueOrNull(u.id),
        "projectId" -> valueOrNull(u.projectId),
        "username" -> JsString(u.username)
      )
    }

    def read(value: JsValue) = {
      value.asJsObject.getFields("id", "projectId", "username", "password") match {
        case Seq(JsNumber(id), JsNumber(projectId), JsString(username), JsString(password)) =>
          new UsersDAO.User(Some(id.toLong), Some(projectId.toLong), username, password)

        case Seq(JsString(username), JsString(password)) =>
          new UsersDAO.User(None, None, username, password)

        case _ => throw new DeserializationException("UsersDAO.User expected")
      }
    }
  }
}

