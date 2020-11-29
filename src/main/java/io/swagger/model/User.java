package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User   {
  @Id
  @JsonProperty("userId")
  @SequenceGenerator(name = "user_seq", initialValue = 100001)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  private Long userId = null;

  @JsonProperty("firstname")
  private String firstname = null;

  @JsonProperty("lastname")
  private String lastname = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("isactive")
  private Boolean isactive = null;

  public User(String firstname, String lastname, String email, String password) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;
    this.isactive = true;
    this.typeofuser = TypeofuserEnum.CUSTOMER;
  }

  /**
   * Gets or Sets typeofuser
   */
  public enum TypeofuserEnum {
    CUSTOMER("customer"),
    
    EMPLOYEE("employee");

    private String value;

    TypeofuserEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeofuserEnum fromValue(String text) {
      for (TypeofuserEnum b : TypeofuserEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("typeofuser")
  private TypeofuserEnum typeofuser = null;

  public User userId(Long userId) {
    this.userId = userId;
    return this;
  }

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

  public User firstname(String firstname) {
    this.firstname = firstname;
    return this;
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

  public User lastname(String lastname) {
    this.lastname = lastname;
    return this;
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

  public User email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "John.Doe@example.com", required = true, description = "")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User password(String password) {
    this.password = password;
    return this;
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

  public void setPassword(String password) {
    this.password = password;
  }

  public User isactive(Boolean isactive) {
    this.isactive = isactive;
    return this;
  }

  /**
   * Get isactive
   * @return isactive
   **/
  @Schema(example = "true", description = "")
  
    public Boolean isIsactive() {
    return isactive;
  }

  public void setIsactive(Boolean isactive) {
    this.isactive = isactive;
  }

  public User typeofuser(TypeofuserEnum typeofuser) {
    this.typeofuser = typeofuser;
    return this;
  }

  /**
   * Get typeofuser
   * @return typeofuser
   **/
  @Schema(description = "")
  
    public TypeofuserEnum getTypeofuser() {
    return typeofuser;
  }

  public void setTypeofuser(TypeofuserEnum typeofuser) {
    this.typeofuser = typeofuser;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(this.userId, user.userId) &&
        Objects.equals(this.firstname, user.firstname) &&
        Objects.equals(this.lastname, user.lastname) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.isactive, user.isactive) &&
        Objects.equals(this.typeofuser, user.typeofuser);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstname, lastname, email, password, isactive, typeofuser);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    firstname: ").append(toIndentedString(firstname)).append("\n");
    sb.append("    lastname: ").append(toIndentedString(lastname)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    isactive: ").append(toIndentedString(isactive)).append("\n");
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
}
