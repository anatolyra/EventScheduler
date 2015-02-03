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
      val response = route(FakeRequest(POST, "/event?username=ori&password=12345").withJsonBody(event)).get

      status(response) must equalTo(OK)
      contentAsString(response) must contain ("ori")
    }

    "return the events of the requested user" in new Context {
      val postEventResponse = route(FakeRequest(POST, "/event?username=asaf&password=1234").withJsonBody(event)).get

      status(postEventResponse) must equalTo(OK)

      val getEventsResponse = route(FakeRequest(GET, "/events?username=asaf&password=1234")).get

      contentAsJson(getEventsResponse) mustEqual Json.toJson(UserEvents(Seq(event)))
    }

    "not allow unknown users in" in new WithApplication{
      val response = route(FakeRequest(GET, "/events?username=unknown&password=1234")).get

      status(response) must equalTo(FORBIDDEN)
      contentAsString(response) must contain("Invalid username or password")
    }

    "not allow known users with incorrect passwords in" in new WithApplication{
      val response = route(FakeRequest(GET, "/events?username=anatoly&password=incorrect")).get

      status(response) must equalTo(FORBIDDEN)
      contentAsString(response) must contain("Invalid username or password")
    }
  }

  implicit def event2Json(event: Event): JsValue = Json.toJson(event)

  trait Context extends WithApplication {
    implicit val timeout = Timeout(100, TimeUnit.MILLISECONDS)

    val event = Event("Meeting", "http://home.com", "0,15,30,45 * * * * ?")
  }
}
