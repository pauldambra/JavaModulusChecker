package com.dambra.paul.moduluschecker;

import java.util.stream.Stream;

public class BankAccount {
    public final String sortCode;
    public final String accountNumber;

    public BankAccount(String sortCode, String accountNumber) {

        this.sortCode = sortCode;
        this.accountNumber = accountNumber;
    }

    public BankAccount(BankAccount bankAccount) {
        this.sortCode = bankAccount.sortCode;
        this.accountNumber = bankAccount.accountNumber;
    }

    public Stream<Integer> allDigits() {
        return Stream.concat(
                As.integerStream(sortCode),
                As.integerStream(accountNumber));
    }

}
