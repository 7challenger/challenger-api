import com.typesafe.config.{ConfigFactory}
import models.User

object WebServer extends App {
  val config = ConfigFactory.load()

  lazy val servicePort = config.getInt("service.port")
  lazy val serviceHost = config.getString("service.host")
}
