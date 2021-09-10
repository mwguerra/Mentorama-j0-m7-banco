package com.mwguerra.models;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

// Conta poupança
public class SavingsAccount extends Account {
  private static final double interestRate = 0.05;

  public SavingsAccount(String owner, Double balance) {
    super(owner, balance, AccountType.SAVINGS);
  }

  @Override
  public Double getBalance() {
    History lastHistoryRecord = database.history
      .stream()
      .filter(h -> Objects.equals(h.getAccountNumber(), accountNumber))
      .findFirst()
      .orElse(null);

    Calendar lastDate = new GregorianCalendar();
    if (lastHistoryRecord != null) {
      lastDate = lastHistoryRecord.getDate();
    }

    int months = monthsBetween(new GregorianCalendar(), lastDate);
    return balance + balance * (interestRate * months);
  }

  public static int monthsBetween(Calendar date1, Calendar date2){
    int months1 = 12 * date1.get(Calendar.YEAR) + date1.get(Calendar.MONTH);
    int months2 = 12 * date2.get(Calendar.YEAR) + date2.get(Calendar.MONTH);

    return Math.abs(months2 - months1);
  }

  @Override
  public boolean withdraw(Double amount) {
    if (amount > balance) {
      return false;
    }

    this.balance = balance - amount;

    return true;
  }
}
