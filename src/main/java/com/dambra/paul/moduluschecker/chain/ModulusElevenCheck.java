package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.WeightRow;
import com.google.common.collect.Streams;

import java.util.function.Function;
import java.util.stream.Stream;

public class ModulusElevenCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        Stream<Integer> weights = CheckForExceptionTwoAndNine(params, selectedRow);

        int total = Streams.zip(
                params.account.allDigits(),
                weights,
                (l, r) -> l * r
        ).reduce(0, Integer::sum);

        int remainder = total % 11;

        return selectedRow.isExceptionFour()
                ? remainder == exceptionFourCheckDigit(params)
                : remainder == 0;
    }

    private int exceptionFourCheckDigit(ModulusCheckParams params) {
        int g = params.account.getNumberAt(BankAccount.G);
        int h = params.account.getNumberAt(BankAccount.H);
        return Integer.parseInt(String.format("%s%s",g,h));
    }

    private Stream<Integer> CheckForExceptionTwoAndNine(ModulusCheckParams params, WeightRow selectedRow) {
        if (!isExceptionTwoAndNine(params, selectedRow)) {
            return selectedRow.weights;
        }

        if (params.account.getNumberAt(BankAccount.A) == 0) {
            return selectedRow.weights;
        }

        if (params.account.getNumberAt(BankAccount.G) == 9) {
            return Stream.of(0, 0, 1, 2, 5, 3, 6, 4, 8, 7, 10, 9, 3, 1);
        } else {
            return Stream.of(0, 0, 0, 0, 0, 0, 0, 0, 8, 7, 10, 9, 3, 1);
        }
    }

    private boolean isExceptionTwoAndNine(ModulusCheckParams params, WeightRow selectedRow) {
        return selectedRow.isExceptionTwo() && secondCheckIsExceptionNine(params);
    }

    private boolean secondCheckIsExceptionNine(ModulusCheckParams params) {
        return params.secondWeightRow.isPresent() && params.secondWeightRow.get().isExceptionNine();
    }
}
