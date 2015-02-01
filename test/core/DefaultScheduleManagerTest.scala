package core

import domain.{DefaultEventScheduler, Event}
import drivers.SchedulerSupport
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
      scheduleManager.addScheduledEvent(event)(userName)

      there was one(scheduler).scheduleJob(job, trigger)
    }

    "fire scheduled events" in pending /*new Context {
      override val scheduler = DefaultEventScheduler.defaultScheduler
      scheduler.
    }*/
  }

  trait Context extends Scope with SchedulerSupport {
    val scheduler = mock[Scheduler]
    val scheduleManager = new DefaultScheduleManager(scheduler)

    lazy val userName = "anatoly"
    lazy val event = Event("123", "234", "0,15,30,45 * * * * ?")
  }
}
