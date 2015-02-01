package core

import domain.{DB, User, UserEvents, Event}
import org.quartz.Scheduler

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
trait EventsManager {
  def addEvent(user: String, event: Event): User
  def getEvents(user: String): UserEvents
}

class UserEventsManager(scheduler: Scheduler) extends EventsManager {
  val scheduleManager = new DefaultScheduleManager(scheduler)

  def addEvent(userName: String, event: Event): User = {
    val user = DB.query[User].whereEqual("name", userName).fetchOne().get

    val savedUserEvents = user.events match {
      case Some(value) => DB.save(value.copy(value.events :+ DB.save(event)))
      case None => DB.save(UserEvents(Seq(DB.save(event))))
    }

    scheduleManager.addScheduledEvent(event)(userName)
    DB.save(user.copy(events = Some(savedUserEvents)))
  }

  def getEvents(userName: String): UserEvents = {
    val userEvents = DB.query[User].whereEqual("name", userName).fetchOne().get.events

    userEvents match {
      case Some(value) => {
        UserEvents(events = value.events map { e => Event(e.title, e.content, e.schedule) })
      }
      case _ => UserEvents(Nil)
    }
  }
}
