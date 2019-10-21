package modules.utils

import slick.jdbc.PostgresProfile.api._
import slick.jdbc.PostgresProfile

object DbProvider {
  var dbConfig: PostgresProfile.backend.Database = null

  def setDb(dbConfigOuter: PostgresProfile.backend.Database) = {
    dbConfig = dbConfigOuter
  }

  def getDb(): PostgresProfile.backend.Database = {
    dbConfig
  }
}