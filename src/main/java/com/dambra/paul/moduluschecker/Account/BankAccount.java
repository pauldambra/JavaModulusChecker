package com.dambra.paul.moduluschecker.Account;

import com.dambra.paul.moduluschecker.As;
import com.google.common.base.Strings;

import java.util.stream.Stream;

public class BankAccount {
    public static final int U = 0, V = 1, W = 2, X = 3, Y = 4, Z = 5,
            A = 6, B = 7, C = 8, D = 9, E = 10, F = 11, G = 12, H = 13;
    public static final String LLOYDS_EURO_SORT_CODE = "309634";

    public final String sortCode;
    public final String accountNumber;

    private BankAccount(String sortCode, String accountNumber) {
        this.sortCode = sortCode;
        this.accountNumber = accountNumber;
    }

    public BankAccount(BankAccount bankAccount) {
        this.sortCode = bankAccount.sortCode;
        this.accountNumber = bankAccount.accountNumber;
    }

    public static BankAccount Of(String sortCode, String accountNumber) {
        String[] corrected = NonStandardAccounts.corrections(sortCode, accountNumber);
        return new BankAccount(corrected[0], corrected[1]);
    }

    public Stream<Integer> allDigits() {
        return Stream.concat(
                As.integerStream(sortCode),
                As.integerStream(accountNumber));
    }

    public int getNumberAt(int i) {
        return allDigits().toArray(Integer[]::new)[i];
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "sortCode='" + sortCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }

    public BankAccount zeroiseUToB() {
        if (getNumberAt(G) == 9) {
            String account = Strings.padStart(accountNumber.substring(2), 8, '0');
            return Of("000000", account);
        }
        return Of(sortCode, accountNumber);
    }

}
