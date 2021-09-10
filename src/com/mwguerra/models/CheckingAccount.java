package com.mwguerra.models;

// Conta corrente
public class CheckingAccount extends Account {
  public Double getOverdraft() {
    return overdraft;
  }

  private Double overdraft = 1000.0;

  public CheckingAccount(String owner, Double balance) {
    super(owner, balance, AccountType.CHECKING);
  }

  @Override
  public Double getBalance() {
    return this.balance;
  }

  @Override
  public boolean withdraw(Double amount) {
    if (amount > balance + overdraft) {
      return false;
    }

    balance = balance - amount;

    return true;
  }
}
