package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.ImmutableList;

import java.util.function.Function;

public final class ModulusElevenCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        ImmutableList<Integer> weights = CheckForExceptionTwoAndNine(params, selectedRow);

        int total = ModulusTotal.calculate(params.getAccount(), weights);
        int remainder = total % 11;

        if (selectedRow.isExceptionFour()) {
            return remainder == exceptionFourCheckDigit(params);
        }
        else {
            boolean result = remainder == 0;
            if (result) { return true; }

            return new LloydsAlternateModulusElevenCheck().check(params, rowSelector);
        }
    }

    private int exceptionFourCheckDigit(ModulusCheckParams params) {
        int g = params.getAccount().getNumberAt(BankAccount.G);
        int h = params.getAccount().getNumberAt(BankAccount.H);
        return Integer.parseInt(String.format("%s%s",g,h));
    }

    private ImmutableList<Integer> CheckForExceptionTwoAndNine(ModulusCheckParams params, WeightRow selectedRow) {
        if (!WeightRow.isExceptionTwoAndNine(params.getFirstWeightRow(), params.getSecondWeightRow())) {
            return selectedRow.getWeights();
        }

        if (params.getAccount().getNumberAt(BankAccount.A) == 0) {
            return selectedRow.getWeights();
        }

        if (params.getAccount().getNumberAt(BankAccount.G) == 9) {
            return ImmutableList.of(0, 0, 0, 0, 0, 0, 0, 0, 8, 7, 10, 9, 3, 1);
        } else {
            return ImmutableList.of(0, 0, 1, 2, 5, 3, 6, 4, 8, 7, 10, 9, 3, 1);
        }
    }

}
