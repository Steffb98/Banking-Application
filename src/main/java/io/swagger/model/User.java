package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"authorities", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
public class User implements UserDetails {
  @Id
  @JsonProperty("userId")
  @SequenceGenerator(name = "user_seq", initialValue = 100001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  private Long userId = null;

  @JsonProperty("firstname")
  private String firstname = null;

  @JsonProperty("lastname")
  private String lastname = null;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("enabled")
  private Boolean enabled = null;

  public User(String firstname, String lastname, String username, String password, TypeofuserEnum typeOfUser) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
    this.enabled = true;
    this.typeofuser = typeOfUser;
  }

  /**
   * Gets or Sets typeofuser
   */

  @JsonProperty("typeofuser")
  private TypeofuserEnum typeofuser = null;

  /**
   * Get userId
   * @return userId
   **/
  @Schema(example = "100001", required = true, description = "")

      public Long getuserId() {
    return userId;
  }

  public void setuserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Get firstname
   * @return firstname
   **/
  @Schema(example = "John", required = true, description = "")
  @NotNull

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  /**
   * Get lastname
   * @return lastname
   **/
  @Schema(example = "Doe", required = true, description = "")
      @NotNull

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return typeofuser.getGrantedAuthorities();
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(example = "Password", required = true, description = "")
      @NotNull

    public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setPassword(String password) {
    this.password = new BCryptPasswordEncoder().encode(password);
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }
  
  public TypeofuserEnum getTypeofuser() {
    return typeofuser;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstname, lastname, username, password, enabled, typeofuser);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    firstname: ").append(toIndentedString(firstname)).append("\n");
    sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    isactive: ").append(toIndentedString(isEnabled())).append("\n");
    sb.append("    typeofuser: ").append(toIndentedString(typeofuser)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  public void setEncryptedPassword(String password){
    this.password = password;
  }
}
