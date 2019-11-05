package modules.auth.utils

import slick.jdbc.PostgresProfile.api._

object Utils {
  def makePassword(password: String): String = {
    password
  }

  def equalityFunc(password: Rep[String], encodedPassword: String): Rep[Boolean] = {
    password === encodedPassword
  }

  def checkPassword(password: Rep[String], encodedPassword: String): Rep[Boolean] = {
    return equalityFunc(password.value, encodedPassword)
  }
}
