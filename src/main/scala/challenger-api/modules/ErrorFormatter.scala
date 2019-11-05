package modules.utils

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

trait ErrorFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit object errorJsonFormat extends RootJsonWriter[Throwable] {
    def write(e: Throwable): JsValue = {
      JsObject(
        "errorMessage" -> JsString(e.getMessage())
      )
    }
  }
}

