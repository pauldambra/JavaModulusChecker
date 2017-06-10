package com.dambra.paul.moduluschecker;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by pauldambra on 10/06/2017.
 */
public class BankAccount {
    private final String sortCode;
    private final String accountNumber;

    public BankAccount(String sortCode, String accountNumber) {

        this.sortCode = sortCode;
        this.accountNumber = accountNumber;
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
