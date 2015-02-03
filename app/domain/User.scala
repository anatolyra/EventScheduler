package domain

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
case class User(name: String, password: String, events: Option[UserEvents]) {
  def checkPassword(password: String): Boolean = this.password == password
}
