package modules.auth.controllers

import scala.concurrent.{Future}
import scala.concurrent.ExecutionContext
import slick.jdbc.PostgresProfile.api._
import akka.stream.alpakka.slick.scaladsl._

import modules.utils.DbProvider
import modules.auth.models.UsersDAO.{User, users}

object UsersController {
  implicit val ec: ExecutionContext = ExecutionContext.global
  lazy val db = DbProvider.getDb()

  def getUserById(userId: Long): Future[User] = {
    val userExistsQuery =
      users.
      filter(_.id === userId).
      take(1).
      exists.
      result

    val userExistsFuture = db.run(userExistsQuery)
    userExistsFuture map {userExists =>
      if (userExists) {
        val userQuery = users.filter(_.id === userId).take(1).result
          db.run(userQuery)
      }

      throw new Exception("no user find")
    }
  }

  def authenticate(username: String, password: String): Future[User] = {
    val userExistsQuery =
      users.
      filter(_.username === username).
      take(1).
      exists.
      result

    val userExistsFuture = db.run(userExistsQuery)

    userExistsFuture map {userExists =>
      if (userExists) {
        val userQuery = users.filter(_.username === username).take(1).result
        val userFuture = db.run(userQuery)

        userFuture map {user =>
          if (user.head.checkPassword(password)) {
            return user.head
          }

          throw new Exception("password incorrect")
        }
      }

      throw new Exception("username incorrect")
    }
  }
}
