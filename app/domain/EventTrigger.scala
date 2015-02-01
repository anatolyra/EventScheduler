package domain

import org.quartz.{CronScheduleBuilder, TriggerBuilder}

/**
 * @author anatolyr 
 * @since 2/1/15        
 */
object EventTrigger {
  def newTriggerWith(name: String, group: String, trigger: String) = {
    TriggerBuilder.newTrigger()
      .withIdentity(name, group)
      .withSchedule(CronScheduleBuilder.cronSchedule(trigger)).build
  }
}
