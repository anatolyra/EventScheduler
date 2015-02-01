package domain

import org.quartz.{JobBuilder, JobExecutionContext, Job}

/**
 * @author anatolyr 
 * @since 2/1/15        
 */
class EventExecutor[T](data: T, action: T => Unit) extends Job {
  override def execute(jobExecutionContext: JobExecutionContext): Unit = action(data)

  def buidWith[T](name: String, group: String) =
    JobBuilder.newJob(classOf[EventExecutor[T]]).withIdentity(name, group).build
}
