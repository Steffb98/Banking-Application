package io.swagger.model;

import java.util.Collection;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

  public User(String firstname, String lastname, String username, String password) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.password = password;
    this.enabled = true;
    this.typeofuser = TypeofuserEnum.CUSTOMER;
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
    this.password = password;
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
}
