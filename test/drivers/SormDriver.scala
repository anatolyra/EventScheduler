package drivers

import domain.{DB, User}

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
trait SormDriver {
  def initDB = {
    DB.save(DB.query[User].whereEqual("name", "anatoly").fetchOne.get.copy(events = None))
    DB.save(DB.query[User].whereEqual("name", "asaf").fetchOne.get.copy(events = None))
    DB.save(DB.query[User].whereEqual("name", "ori").fetchOne.get.copy(events = None))
  }
}
