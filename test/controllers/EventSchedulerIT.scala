package controllers

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import domain.Event._
import domain.UserEvents._
import domain.{Event, UserEvents}
import play.api.libs.json._
import play.api.test.Helpers._
import play.api.test._
import support.SequentialSpecification

/**
 * @author anatolyr 
 * @since 1/28/15        
 */
class EventSchedulerIT extends SequentialSpecification {
  "Event scheduler" should {
    "return a status code 200 with the user name for which the event was added" in new Context {
      val request = FakeRequest(POST, "/event?user=ori").withJsonBody(event)
      val result = route(request).get

      contentAsString(result) must contain ("ori")
    }

    "return the events of the requested user" in new Context {
      val postEvent = route(FakeRequest(POST, "/event?user=asaf").withJsonBody(event)).get

      status(postEvent) must equalTo(OK)

      val getEvents = route(FakeRequest(GET, "/events?user=asaf")).get

      contentAsJson(getEvents) mustEqual Json.toJson(UserEvents(Seq(event)))
    }
  }

  implicit def event2Json(event: Event): JsValue = Json.toJson(event)

  trait Context extends WithApplication {
    implicit val timeout = Timeout(100, TimeUnit.MILLISECONDS)

    val event = Event("Meeting", "http://home.com", "0,15,30,45 * * * * ?")
  }
}
