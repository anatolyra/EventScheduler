package controllers

import domain.{Event, DB, User}
import play.api.mvc._

/**
 * @author anatolyr 
 * @since 2/3/15        
 */
trait SecureAction {
  self: Controller =>

  def SecureAction(f: Event => Result) = Action { implicit request =>
    val user = AuthUtils.parseUserFromQueryString
    val event = request.body.asJson.get.as[Event]

      user match {
        case Some(v) => f(event)
        case _ => Forbidden(s"Invalid username or password.")
      }
  }

  def SecureAction(f: => Result) = Action { implicit request =>
    val user = AuthUtils.parseUserFromQueryString

    user match {
      case Some(v) => f
      case _ => Forbidden(s"Invalid username or password.")
    }
  }
}

object AuthUtils {
  def parseUserFromQueryString(implicit request:RequestHeader) = {
    val query = request.queryString.map { case (k, v) => k -> v.mkString }
    val username = query get ("username")
    val password = query get ("password")
    (username, password) match {
      case (Some(u), Some(p)) => {
        DB.query[User].whereEqual("name", u).fetchOne match {
          case Some(value) => Some(value).filter(user => user.checkPassword(p))
          case _ => None
        }
      }
      case _ => None
    }
  }
}
