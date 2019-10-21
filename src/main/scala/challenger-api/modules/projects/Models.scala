package modules.projects.models

import slick.jdbc.PostgresProfile.api._

case class Project(id: Long, name: String, description: String)

class Projects(tag: Tag) extends Table[Project](tag, "projects") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def description = column[String]("description")

  def * = (id, name, description) <> (Project.tupled, Project.unapply)
}
