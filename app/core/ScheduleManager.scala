package core

import domain.{EventTrigger, EventExecutor, Event}
import org.quartz.Scheduler

/**
 * @author anatolyr 
 * @since 2/1/15        
 */
trait ScheduleManager {
  def addScheduledEvent(event: Event)(implicit user: String)
}

class DefaultScheduleManager(scheduler: Scheduler) extends ScheduleManager {
  def addScheduledEvent(event: Event)(implicit user: String): Unit = {
    val job = new EventExecutor[Event](event, Event => Unit).buidWith(event.title, user)
    val trigger = EventTrigger.newTriggerWith(event.title, user, event.schedule)

    scheduler.scheduleJob(job, trigger)
  }
}
