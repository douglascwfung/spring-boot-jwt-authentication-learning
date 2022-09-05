package net.icestone.models;

import java.time.Instant;
import javax.persistence.*;
@Entity(name = "refreshtoken")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
  @Column(nullable = false, unique = true)
  private String token;
  @Column(nullable = false)
  private Instant expiryDate;
/**
 * @return the id
 */
public long getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(long id) {
	this.id = id;
}
/**
 * @return the user
 */
public User getUser() {
	return user;
}
/**
 * @param user the user to set
 */
public void setUser(User user) {
	this.user = user;
}
/**
 * @return the token
 */
public String getToken() {
	return token;
}
/**
 * @param token the token to set
 */
public void setToken(String token) {
	this.token = token;
}
/**
 * @return the expiryDate
 */
public Instant getExpiryDate() {
	return expiryDate;
}
/**
 * @param expiryDate the expiryDate to set
 */
public void setExpiryDate(Instant expiryDate) {
	this.expiryDate = expiryDate;
}
  
  

}