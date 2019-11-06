package modules.auth.models

import scala.concurrent.Future
import scala.util.{Success, Failure}
import slick.jdbc.PostgresProfile.api._

import modules.auth.utils.Utils.{makePassword}
import modules.projects.models.ProjectsDAO

object UsersDAO {
  case class User(
    id: Option[Long],
    projectId: Option[Long],
    username: String,
    password: String
  ) {

    // TODO: add validations on fields

    var _password: String = null

    def password_ (value: String) = {
      _password = makePassword(value)
    }
  }

  case class LoginParams(username: String, password: String)

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def username = column[String]("username", O.Unique)
    def password = column[String]("password")
    def projectId = column[Long]("project_id")

    def project = foreignKey(
      "project", projectId, ProjectsDAO.projects)(_.id, onDelete=ForeignKeyAction.Cascade)

    def * = (id.?, projectId.?, username, password) <> (User.tupled, User.unapply)
  }

  lazy val users = TableQuery[Users]
}
