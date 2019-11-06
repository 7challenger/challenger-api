package modules.projects.controllers

import scala.concurrent.{Future}
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext
import slick.jdbc.PostgresProfile.api._
import akka.stream.alpakka.slick.scaladsl._

import modules.utils.DbProvider
import modules.projects.models.ProjectsDAO.{Project, projects}

object ProjectsConotroller {
  // TODO: move to aktors and own ec
  implicit val ec: ExecutionContext = ExecutionContext.global

  // TODO: inject db instead of using this crap
  lazy val db = DbProvider.getDb()

  def getProjectById(projectId: Long): Future[Project] = {
    val maybeProjectQuery = projects.filter(_.id === projectId).take(1).result.headOption

    val maybeProjectFuture = db.run(maybeProjectQuery)
    maybeProjectFuture map {
      case Some(project) => project
      // TODO: deal with error messages
      case None => throw new Exception("no project found")
    }
  }

}

