package org.rapidpm.vaadin.trainer.api.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 */
public class CalcResult {

  private Long          id;
  private Double        opA;
  private Double        opB;
  private String        operator;
  private Double        resultMachine;
  private String        resultHuman;
  private Boolean       resultOK;
  private LocalDateTime timestamp;

  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CalcResult)) return false;
    CalcResult that = (CalcResult) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "CalcResult{" +
           "id=" + id +
           ", opA=" + opA +
           ", opB=" + opB +
           ", operator='" + operator + '\'' +
           ", resultMachine=" + resultMachine +
           ", resultHuman=" + resultHuman +
           ", resultOK=" + resultOK +
           ", timestamp=" + timestamp +
           ", user=" + user +
           '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getOpA() {
    return opA;
  }

  public void setOpA(Double opA) {
    this.opA = opA;
  }

  public Double getOpB() {
    return opB;
  }

  public void setOpB(Double opB) {
    this.opB = opB;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Double getResultMachine() {
    return resultMachine;
  }

  public void setResultMachine(Double resultMachine) {
    this.resultMachine = resultMachine;
  }

  public String getResultHuman() {
    return resultHuman;
  }

  public void setResultHuman(String resultHuman) {
    this.resultHuman = resultHuman;
  }

  public Boolean getResultOK() {
    return resultOK;
  }

  public void setResultOK(Boolean resultOK) {
    this.resultOK = resultOK;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
