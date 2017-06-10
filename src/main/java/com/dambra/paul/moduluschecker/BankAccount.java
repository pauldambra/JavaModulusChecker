package com.dambra.paul.moduluschecker;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by pauldambra on 10/06/2017.
 */
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

    public List<Integer> allDigits() {
        Stream<Integer> sc = sortCode
                .chars()
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .map(Integer::parseInt);

        Stream<Integer> an = accountNumber
                .chars()
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .map(Integer::parseInt);

        return Stream.concat(sc, an).collect(ImmutableList.toImmutableList());
    }
}
