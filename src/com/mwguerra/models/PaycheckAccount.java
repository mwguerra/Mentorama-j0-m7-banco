package com.mwguerra.models;

// Conta salário
public class PaycheckAccount extends Account {
  private static final int WITHDRAWS_PER_MONTH = 2;
  private static int withdrawsCount = 0;

  public PaycheckAccount(String owner, Double balance) {
    super(owner, balance, AccountType.PAYCHECK);
  }

  @Override
  public Double getBalance() {
    return this.balance;
  }

  @Override
  public boolean withdraw(Double amount) {
    if (withdrawsCount >= WITHDRAWS_PER_MONTH) {
      return false;
    }

    if (amount > balance) {
      return false;
    }

    this.balance = balance - amount;

    withdrawsCount++;
    return true;
  }
}
