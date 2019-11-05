import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{ Matchers, WordSpec }
import org.scalatest.concurrent.ScalaFutures

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.model._
import akka.http.scaladsl.server._

import modules.auth.router.UsersRouter
import modules.auth.marshallers.UserFormatter
import modules.auth.models.UsersDAO.{LoginParams}

@RunWith(classOf[JUnitRunner])
class AuthRouterTest
    extends WordSpec
    with Matchers
    with ScalatestRouteTest
    with ScalaFutures
    with UserFormatter {

  "authenticateRoute" should {
    "return user if creds are right" in {
      val loginParams = LoginParams("testusername", "testpassword")
      val loginParamsEntity = Marshal(loginParams).to[MessageEntity].futureValue
      val request = Post("/authenticate").withEntity(loginParamsEntity)

      request ~> UsersRouter.routes ~> check {
        status shouldEqual StatusCodes.OK
        contentType shouldEqual ContentTypes.`application/json`
      }
    }
  }
}

