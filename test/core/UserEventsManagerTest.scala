package core

import domain.{EventTrigger, EventExecutor, Event, UserEvents}
import drivers.SormDriver
import org.quartz.Scheduler
import org.specs2.matcher.{ThrownExpectations, Matcher}
import org.specs2.mock.Mockito
import org.specs2.mutable.{After, Before, SpecificationWithJUnit}
import org.specs2.specification.Scope

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
class UserEventsManagerTest extends SpecificationWithJUnit with Mockito with ThrownExpectations {
  "EventsManager" should {
    "add the given event to the specified user" in new Context {
      eventsManager.addEvent(userName, event)

      eventsManager.getEvents(userName) must beWith(Seq(event))
    }

    "add several events and then retrieve them all" in new Context {
      val event1 = Event("123", "234", "0,15,30,45 * * * * ?")
      val event2 = Event("asd", "dfg", "0,15,30,45 * * * * ?")
      val event3 = Event("qwe", "iuy", "0,15,30,45 * * * * ?")

      eventsManager.addEvent(userName, event1)
      eventsManager.addEvent(userName, event2)
      eventsManager.addEvent(userName, event3)

      eventsManager.getEvents(userName) must beWith(Seq(event2, event1, event3))
    }

    "add an event to the event scheduler" in new Context {
      eventsManager.addEvent(userName, event)

      there was one(scheduler).scheduleJob(job, trigger)
    }
  }

  def beWith(events: Seq[Event]): Matcher[UserEvents] = {
    events.contain(===) ^^ { (_: UserEvents).events aka "events"}
  }

  trait Context extends Scope with SormDriver with After with Before {
    val scheduler = mock[Scheduler]
    val eventsManager = new UserEventsManager(scheduler)

    val userName = "anatoly"
    val event = Event("123", "234", "0,15,30,45 * * * * ?")

    val job = new EventExecutor[Event](event, Event => Unit).buidWith(event.title, userName)
    val trigger = EventTrigger.newTriggerWith(event.title, userName, event.schedule)

    override def after = {
      initDB
    }

    override def before = {
      initDB
    }
  }
}
