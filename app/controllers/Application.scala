package controllers

import core.UserEventsManager
import domain.DefaultEventScheduler
import play.api.libs.json.Json
import play.api.mvc._

object Application extends Controller with SecureAction {
  val eventManager = new UserEventsManager(DefaultEventScheduler.defaultScheduler)

  def index = Action {
    Ok(views.html.index("My new application is ready."))
  }

  def addEvent(username: String, password: String) = SecureAction { event =>
    eventManager.addEvent(username, event)

    Ok(username)
  }

  def getEvents(username: String, password: String) = SecureAction {
    val events = Json.toJson(eventManager.getEvents(username))

    Ok(events)
  }
}