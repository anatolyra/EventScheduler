package domain

import sorm._

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
object DB
  extends Instance(entities = Seq(Entity[User](), Entity[Event](), Entity[UserEvents]()), url = "jdbc:h2:mem:test") {

  DB.save(User("anatoly", "123", None))
  DB.save(User("asaf", "1234", None))
  DB.save(User("ori", "12345", None))
}
