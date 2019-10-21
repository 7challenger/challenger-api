package modules.auth.models

import scala.concurrent.Future
import scala.util.{Success, Failure}
import slick.jdbc.PostgresProfile.api._

import modules.auth.utils.Utils.{makePassword}
import modules.projects.models.{Project, Projects}

object UsersDAO {
  case class User(
    id: Long,
    projectId: Long,
    username: String,
    password: String
  ) {

    var _password: String = null

    def password_ (value: String) = {
      _password = makePassword(value)
    }

    def checkPassword(
      password: String,
      encodedPassword: String = this.password
    ): Boolean = {
      if (password == encodedPassword) {
        true
      }

      false
    }
  }

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def username = column[String]("username")
    def password = column[String]("password")
    def projectId = column[Long]("project_id")

    lazy val projects = TableQuery[Projects]
    def project = foreignKey("project", projectId, projects)(_.id, onDelete=ForeignKeyAction.Cascade)

    def * = (id, projectId, username, password) <> (User.tupled, User.unapply)
  }

  lazy val users = TableQuery[Users]
}
