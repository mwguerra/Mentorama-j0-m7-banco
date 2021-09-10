package com.mwguerra.models;

import com.mwguerra.database.Database;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class History {
  private String description;
  private Calendar date;
  private Integer accountNumber;
  private OperationType operationType;
  private Double amount;

  protected Database database;

  public String getDescription() {
    return description;
  }

  public Calendar getDate() {
    return date;
  }

  @Override
  public String toString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    return "[" + dateFormat.format(this.getDate().getTime()) + "] " +
    (this.getOperationType() == OperationType.DEPOSIT ? "+" : "-") +
    this.getAmount() + " ("+ this.getDescription() + ")";
  }

  public Integer getAccountNumber() {
    return accountNumber;
  }

  public OperationType getOperationType() {
    return operationType;
  }

  public Double getAmount() {
    return amount;
  }

  public History setDatabase(Database database) {
    this.database = database;
    return this;
  }

  public History(Integer accountNumber, String description, Calendar date, OperationType operationType, Double amount) {
    this.accountNumber = accountNumber;
    this.description = description;
    this.date = date;
    this.operationType = operationType;
    this.amount = amount;
  }

  public void save() {
    database.history.add(this);
  }

  public static List<History> all(Database database, Integer accountNumber) {
    return database.history
      .stream()
      .filter(h -> Objects.equals(h.accountNumber, accountNumber))
      .collect(Collectors.toList());
  }
}
