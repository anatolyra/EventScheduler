package domain

/**
 * @author anatolyr 
 * @since 1/31/15        
 */
case class User(name: String, pass: String, events: Option[UserEvents])
