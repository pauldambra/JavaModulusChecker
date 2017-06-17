package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;

import java.util.function.Function;

public final class ModulusElevenCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        ImmutableList<Integer> weights = CheckForExceptionTwoAndNine(params, selectedRow);

        int total = Streams.zip(
                params.getAccount().allDigits(),
                weights.stream(),
                (l, r) -> l * r
        ).reduce(0, Integer::sum);

        int remainder = total % 11;

        boolean isExceptionFour = selectedRow.isExceptionFour();
        return isExceptionFour ? remainder == exceptionFourCheckDigit(params) : remainder == 0;
    }

    private int exceptionFourCheckDigit(ModulusCheckParams params) {
        int g = params.getAccount().getNumberAt(BankAccount.G);
        int h = params.getAccount().getNumberAt(BankAccount.H);
        return Integer.parseInt(String.format("%s%s",g,h));
    }

    private ImmutableList<Integer> CheckForExceptionTwoAndNine(ModulusCheckParams params, WeightRow selectedRow) {
        if (!WeightRow.isExceptionTwoAndNine(java.util.Optional.ofNullable(selectedRow), params.getSecondWeightRow())) {
            return selectedRow.getWeights();
        }

        if (params.getAccount().getNumberAt(BankAccount.A) == 0) {
            return selectedRow.getWeights();
        }

        if (params.getAccount().getNumberAt(BankAccount.G) == 9) {
            return ImmutableList.of(0, 0, 1, 2, 5, 3, 6, 4, 8, 7, 10, 9, 3, 1);
        } else {
            return ImmutableList.of(0, 0, 0, 0, 0, 0, 0, 0, 8, 7, 10, 9, 3, 1);
        }
    }

}
