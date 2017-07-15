package com.github.pauldambra.moduluschecker.valacdosFile;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.google.common.collect.ImmutableList;

public class ModulusElevenWeightsTransformer {
    public static ImmutableList<Integer> CheckForExceptionTwoAndNine(
            ModulusCheckParams params, WeightRow selectedRow) {

        if (!WeightRow.isExceptionTwoAndNine(
                params.firstWeightRow,
                params.secondWeightRow)) {
            return selectedRow.getWeights();
        }

        if (params.account.getNumberAt(BankAccount.A) == 0) {
            return selectedRow.getWeights();
        }

        if (params.account.getNumberAt(BankAccount.G) == 9) {
            return ImmutableList.of(0, 0, 0, 0, 0, 0, 0, 0, 8, 7, 10, 9, 3, 1);
        } else {
            return ImmutableList.of(0, 0, 1, 2, 5, 3, 6, 4, 8, 7, 10, 9, 3, 1);
        }
    }
}
