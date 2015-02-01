package domain

import org.quartz.{Job, JobExecutionContext}
import play.api.libs.json._

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
case class Event(title: String, content: String, schedule: String)

object Event {
  implicit val eventFormat = Json.format[Event]
}
