package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")


public class Account   {
  @JsonProperty("iban")
  private String iban = null;

  @JsonProperty("balance")
  private BigDecimal balance = null;

  /**
   * Gets or Sets typeofaccount
   */
  public enum TypeofaccountEnum {
    SAVING("saving"),
    
    DEPOSIT("deposit");

    private String value;

    TypeofaccountEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeofaccountEnum fromValue(String text) {
      for (TypeofaccountEnum b : TypeofaccountEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("typeofaccount")
  private TypeofaccountEnum typeofaccount = null;

  @JsonProperty("absolutlimit")
  private BigDecimal absolutlimit = null;

  @JsonProperty("isactive")
  private Boolean isactive = null;

  @JsonProperty("userid")
  private Long userid = null;

  @JsonProperty("daylimit")
  private Long daylimit = null;

  @JsonProperty("transactionlimit")
  private BigDecimal transactionlimit = null;

  @JsonProperty("numberoftransactions")
  private Long numberoftransactions = null;

  public Account iban(String iban) {
    this.iban = iban;
    return this;
  }

  /**
   * Get iban
   * @return iban
   **/
  @Schema(example = "NL00RABO0123456789", required = true, description = "")
      @NotNull

    public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public Account balance(BigDecimal balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   **/
  @Schema(example = "10.00", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Account typeofaccount(TypeofaccountEnum typeofaccount) {
    this.typeofaccount = typeofaccount;
    return this;
  }

  /**
   * Get typeofaccount
   * @return typeofaccount
   **/
  @Schema(required = true, description = "")
      @NotNull

    public TypeofaccountEnum getTypeofaccount() {
    return typeofaccount;
  }

  public void setTypeofaccount(TypeofaccountEnum typeofaccount) {
    this.typeofaccount = typeofaccount;
  }

  public Account absolutlimit(BigDecimal absolutlimit) {
    this.absolutlimit = absolutlimit;
    return this;
  }

  /**
   * Get absolutlimit
   * @return absolutlimit
   **/
  @Schema(example = "-10.00", description = "")
  
    @Valid
    public BigDecimal getAbsolutlimit() {
    return absolutlimit;
  }

  public void setAbsolutlimit(BigDecimal absolutlimit) {
    this.absolutlimit = absolutlimit;
  }

  public Account isactive(Boolean isactive) {
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

  public Account userid(Long userid) {
    this.userid = userid;
    return this;
  }

  /**
   * Get userid
   * @return userid
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Long getUserid() {
    return userid;
  }

  public void setUserid(Long userid) {
    this.userid = userid;
  }

  public Account daylimit(Long daylimit) {
    this.daylimit = daylimit;
    return this;
  }

  /**
   * Maximum number of transactions per day
   * @return daylimit
   **/
  @Schema(example = "5", description = "Maximum number of transactions per day")
  
    public Long getDaylimit() {
    return daylimit;
  }

  public void setDaylimit(Long daylimit) {
    this.daylimit = daylimit;
  }

  public Account transactionlimit(BigDecimal transactionlimit) {
    this.transactionlimit = transactionlimit;
    return this;
  }

  /**
   * Maximum amount per transaction
   * @return transactionlimit
   **/
  @Schema(example = "20000.00", description = "Maximum amount per transaction")
  
    @Valid
    public BigDecimal getTransactionlimit() {
    return transactionlimit;
  }

  public void setTransactionlimit(BigDecimal transactionlimit) {
    this.transactionlimit = transactionlimit;
  }

  public Account numberoftransactions(Long numberoftransactions) {
    this.numberoftransactions = numberoftransactions;
    return this;
  }

  /**
   * Number of transactions, resets daily
   * @return numberoftransactions
   **/
  @Schema(example = "0", description = "Number of transactions, resets daily")
  
    public Long getNumberoftransactions() {
    return numberoftransactions;
  }

  public void setNumberoftransactions(Long numberoftransactions) {
    this.numberoftransactions = numberoftransactions;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.iban, account.iban) &&
        Objects.equals(this.balance, account.balance) &&
        Objects.equals(this.typeofaccount, account.typeofaccount) &&
        Objects.equals(this.absolutlimit, account.absolutlimit) &&
        Objects.equals(this.isactive, account.isactive) &&
        Objects.equals(this.userid, account.userid) &&
        Objects.equals(this.daylimit, account.daylimit) &&
        Objects.equals(this.transactionlimit, account.transactionlimit) &&
        Objects.equals(this.numberoftransactions, account.numberoftransactions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iban, balance, typeofaccount, absolutlimit, isactive, userid, daylimit, transactionlimit, numberoftransactions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    iban: ").append(toIndentedString(iban)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    typeofaccount: ").append(toIndentedString(typeofaccount)).append("\n");
    sb.append("    absolutlimit: ").append(toIndentedString(absolutlimit)).append("\n");
    sb.append("    isactive: ").append(toIndentedString(isactive)).append("\n");
    sb.append("    userid: ").append(toIndentedString(userid)).append("\n");
    sb.append("    daylimit: ").append(toIndentedString(daylimit)).append("\n");
    sb.append("    transactionlimit: ").append(toIndentedString(transactionlimit)).append("\n");
    sb.append("    numberoftransactions: ").append(toIndentedString(numberoftransactions)).append("\n");
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
