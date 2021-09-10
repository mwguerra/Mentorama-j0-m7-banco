package com.mwguerra.database;

import com.mwguerra.models.*;

public class DatabaseSeeder {
  private Database database;

  public DatabaseSeeder setDatabase(Database database) {
    this.database = database;
    return this;
  }

  public Account[] handle() {
    CheckingAccount checkingAccount = new CheckingAccount(
      "123.456.789-00",
      200.00
    );
    checkingAccount.setDatabase(database);
    database.checkingAccounts.add(checkingAccount);

    PaycheckAccount paycheckAccount = new PaycheckAccount(
      "123.456.789-00",
      5000.00
    );
    paycheckAccount.setDatabase(database);
    database.paycheckAccounts.add(paycheckAccount);

    SavingsAccount savingsAccount = new SavingsAccount(
      "123.456.789-00",
      13000.00
    );
    savingsAccount.setDatabase(database);
    database.savingsAccounts.add(savingsAccount);

    return new Account[]{
      checkingAccount,
      paycheckAccount,
      savingsAccount
    };
  }
}
