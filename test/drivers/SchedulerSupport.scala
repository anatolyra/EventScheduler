package drivers

import domain.{EventTrigger, EventExecutor, Event}

/**
 * @author anatolyr 
 * @since 2/1/15        
 */
trait SchedulerSupport {
  def userName: String
  def event: Event

  val job = new EventExecutor[Event](event, Event => Unit).buidWith(event.title, userName)
  val trigger = EventTrigger.newTriggerWith(event.title, userName, event.schedule)
}
