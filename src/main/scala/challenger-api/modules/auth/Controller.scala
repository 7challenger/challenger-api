package modules.auth.controller

import scala.concurrent.{Future}
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext
import slick.jdbc.PostgresProfile.api._
import akka.stream.alpakka.slick.scaladsl._

import modules.utils.DbProvider
import modules.auth.utils.Utils.{checkPassword}
import modules.auth.models.UsersDAO.{User, users}

// TODO: add abstract controller with all CRUD
object UsersController {
  // TODO: move to aktors and own ec
  implicit val ec: ExecutionContext = ExecutionContext.global

  // TODO: inject db instead of using this crap
  lazy val db = DbProvider.getDb()

  def getUserById(userId: Long): Future[User] = {
    val maybeUserQuery = users.filter(_.id === userId).take(1).result.headOption

    val maybeUserFuture = db.run(maybeUserQuery)
    maybeUserFuture map {
      case Some(user) => user
      // TODO: deal with error messages
      case None => throw new Exception("no user found")
    }
  }

  def authenticate(username: String, password: String): Future[User] = {
    val userQuery = users.
      filter(u => (u.username === username) && (checkPassword(u.password, password))).
      take(1).result.headOption

    val userFuture = db.run(userQuery)
    userFuture map {
      case Some(user) => user
      // TODO: deal with error messages
      case None => throw new Exception("invalid creds")
    }
  }

  def create(username: String, password: String): Future[User] = {
    lazy val user = User(None, None, username, password)
    users += user

    val userWithId = (users returning users.map(_.id)
      into ((user, id) => user.copy(id = Some(id)))
    ) += user

    // TODO: deal with error messages
    db.run(userWithId)
  }

  // def update(userId: Long, u: User): Future[User] = {
  //   val updatedUser = User(None, u.projectId, u.username)

  //   users.filter(_.id === userId).take(1).insertOrUpdate(updatedUser)
  // }
}
