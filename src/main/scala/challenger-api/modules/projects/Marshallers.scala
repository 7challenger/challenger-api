package modules.projects.marshallers

import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import modules.projects.models.ProjectsDAO

trait ProjectFormatter extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val projectFormat = jsonFormat3(ProjectsDAO.Project)
}

