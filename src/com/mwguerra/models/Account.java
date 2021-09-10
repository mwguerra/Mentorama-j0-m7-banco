package com.mwguerra.models;

import com.mwguerra.database.Database;

import java.util.Random;

public abstract class Account {
  protected String owner;
  protected Integer accountNumber;
  protected Double initialBalance;
  protected Double balance;
  protected AccountType type;
  protected Database database;

  public Account(String owner, Double balance, AccountType type) {
    this.owner = owner;
    this.accountNumber = createNewAccountNumber();
    this.initialBalance = balance;
    this.balance = balance;
    this.type = type;
  }

  private Integer createNewAccountNumber() {
    Random r = new Random();
    String randomNumber = String.format("%012d", r.nextInt(1001));
    return Integer.parseInt(randomNumber);
  }

  public void setDatabase(Database database) {
    this.database = database;
  }

  public abstract Double getBalance();

  public void setBalance(Double balance) {
    this.balance = balance;
  };

  public abstract boolean withdraw(Double amount);

  public void deposit(Double amount) {
    balance = balance + amount;
  };

  public Double getInitialBalance() {
    return initialBalance;
  }

  public Integer getAccountNumber() {
    return accountNumber;
  }
}
