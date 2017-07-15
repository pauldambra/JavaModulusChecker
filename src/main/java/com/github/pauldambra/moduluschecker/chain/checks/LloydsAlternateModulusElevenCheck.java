package com.github.pauldambra.moduluschecker.chain.checks;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;
import java.util.function.Function;

public final class LloydsAlternateModulusElevenCheck {
    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        if (!WeightRow.isExceptionTwoAndNine(
                params.firstWeightRow,
                params.secondWeightRow)) {
            return false;
        }

        BankAccount account = BankAccount.Of(BankAccount.LLOYDS_EURO_SORT_CODE, params.account.accountNumber);

        int total = ModulusTotal.calculate(account, selectedRow.getWeights());
        return total % 11 == 0;
    }
}
