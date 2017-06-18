package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.Streams;

import java.util.function.Function;

public final class LloydsAlternateModulusElevenCheck {
    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        if (!WeightRow.isExceptionTwoAndNine(params.getFirstWeightRow(), params.getSecondWeightRow())) {
            return false;
        }

        BankAccount account = new BankAccount(BankAccount.LLOYDS_EURO_SORT_CODE, params.getAccount().accountNumber);

        int total = Streams.zip(
                account.allDigits(),
                selectedRow.getWeights().stream(),
                (l, r) -> l * r
        ).reduce(0, Integer::sum);

        return total % 11 == 0;
    }
}
