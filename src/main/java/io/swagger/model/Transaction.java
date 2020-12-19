package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.threeten.bp.LocalDateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2020-11-21T13:18:37.550Z[GMT]")

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction   {
  @Id
  @SequenceGenerator(name = "transaction_seq", initialValue = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("sender")
  private String sender = null;

  @JsonProperty("receiver")
  private String receiver = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("performinguser")
  private Long performinguser = null;

  @JsonProperty("receivinguser")
  private Long receivinguser = null;

  @JsonProperty("date")
  private LocalDateTime date = null;

  public Transaction(String sender, String receiver, BigDecimal amount, Long performinguser, Long receivinguser) {
    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.performinguser = performinguser;
    this.date = LocalDateTime.now();
    this.receivinguser = receivinguser;
  }

  public Transaction id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(required = true, description = "")

    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Transaction sender(String sender) {
    this.sender = sender;
    return this;
  }

  /**
   * iban form the sender account
   * @return sender
   **/
  @Schema(required = true, description = "iban form the sender account")
      @NotNull

    public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public Transaction receiver(String receiver) {
    this.receiver = receiver;
    return this;
  }

  /**
   * iban from the receiver account
   * @return receiver
   **/
  @Schema(required = true, description = "iban from the receiver account")
      @NotNull

    public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public Transaction amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Transaction performinguser(Long performinguser) {
    this.performinguser = performinguser;
    return this;
  }

  /**
   * UserId from the user performing transaction
   * @return performinguser
   **/
  @Schema(required = true, description = "UserId from the user performing transaction")
      @NotNull

    public Long getPerforminguser() {
    return performinguser;
  }

  public void setPerforminguser(Long performinguser) {
    this.performinguser = performinguser;
  }

  public Long getReceivinguser() {
    return receivinguser;
  }

  public void setReceivinguser(Long receivinguser) {
    this.receivinguser = receivinguser;
  }

  public Transaction date(LocalDateTime date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   **/
  @Schema(description = "")
  
    @Valid
    public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.id, transaction.id) &&
        Objects.equals(this.sender, transaction.sender) &&
        Objects.equals(this.receiver, transaction.receiver) &&
        Objects.equals(this.amount, transaction.amount) &&
        Objects.equals(this.performinguser, transaction.performinguser) &&
        Objects.equals(this.date, transaction.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, sender, receiver, amount, performinguser, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    sender: ").append(toIndentedString(sender)).append("\n");
    sb.append("    receiver: ").append(toIndentedString(receiver)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    performinguser: ").append(toIndentedString(performinguser)).append("\n");
    sb.append("    date: ").append(toIndentedString(date.withNano(0))).append("\n");
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
