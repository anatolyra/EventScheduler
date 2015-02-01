package core

import domain.{EventTrigger, EventExecutor, Event}
import org.quartz.Scheduler
import org.specs2.matcher.ThrownExpectations
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

/**
 * @author anatolyr 
 * @since 2/1/15        
 */
class DefaultScheduleManagerTest extends SpecificationWithJUnit with Mockito with ThrownExpectations {
  "ScheduleManager" should {
    "add an event to the scheduler" in new Context {
      scheduleManager.addScheduledEvent(event)

      there was one(scheduler).scheduleJob(job, trigger)
    }

    "fire scheduled events" in pending
  }

  trait Context extends Scope {
    implicit val user = "anatoly"
    val scheduler = mock[Scheduler]
    val scheduleManager = new DefaultScheduleManager(scheduler)

    val event = Event("123", "234", "0,15,30,45 * * * * ?")

    val job = new EventExecutor[Event](event, Event => Unit).buidWith(event.title, user)
    val trigger = EventTrigger.newTriggerWith(event.title, user, event.schedule)
  }
}
