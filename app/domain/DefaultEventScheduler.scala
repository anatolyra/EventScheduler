package domain

import org.quartz.impl.StdSchedulerFactory

/**
 * @author anatolyr 
 * @since 2/1/15        
 */
object DefaultEventScheduler {
  val scheduler = new StdSchedulerFactory().getScheduler
  scheduler.start

  def defaultScheduler = scheduler
}
