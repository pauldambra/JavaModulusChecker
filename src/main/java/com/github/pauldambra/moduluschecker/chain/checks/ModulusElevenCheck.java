package com.github.pauldambra.moduluschecker.chain.checks;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusElevenWeightsTransformer;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.ImmutableList;

import java.util.function.Function;

public final class ModulusElevenCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        ImmutableList<Integer> weights = ModulusElevenWeightsTransformer.CheckForExceptionTwoAndNine(params, selectedRow);

        int total = ModulusTotal.calculate(params.account, weights);
        int remainder = total % 11;

        if (selectedRow.isException(4)) {
            return remainder == exceptionFourCheckDigit(params);
        }
        else {
            boolean result = remainder == 0;
            if (result) { return true; }

            return new LloydsAlternateModulusElevenCheck().check(params, rowSelector);
        }
    }

    private int exceptionFourCheckDigit(ModulusCheckParams params) {
        int g = params.account.getNumberAt(BankAccount.G);
        int h = params.account.getNumberAt(BankAccount.H);
        return Integer.parseInt(String.format("%s%s",g,h));
    }

}
