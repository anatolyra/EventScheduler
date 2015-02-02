package controllers

import core.UserEventsManager
import domain.{DefaultEventScheduler, Event}
import domain.Event._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._

object Application extends Controller {
  val eventManager = new UserEventsManager(DefaultEventScheduler.defaultScheduler)

  def index = Action {
    Ok(views.html.index("My new application is ready."))
  }

  def addEvent(userName: String) = Action { request =>
    val event = request.body.asJson.get.as[Event]

    eventManager.addEvent(userName, event)

    Ok(userName)
  }

  def getEvents(userName: String) = Action {
    Ok(Json.toJson(eventManager.getEvents(userName)))
  }
}