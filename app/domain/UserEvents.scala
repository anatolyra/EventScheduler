package domain

import play.api.libs.json.Json

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
case class UserEvents(events: Seq[Event])

object UserEvents {
  implicit val userEventsFormat = Json.format[UserEvents]
}
