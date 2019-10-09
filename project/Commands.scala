import sbt._
import Keys._

// imports standard command parsing functionality
import complete.DefaultParsers._

object Commands {
  def helloAll = Command.args("helloAll", "<name>") { (state, args) =>
    println("Hi " + args.mkString(" "))
    state
  }
}