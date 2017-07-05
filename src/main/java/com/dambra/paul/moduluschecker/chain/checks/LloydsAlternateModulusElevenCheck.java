package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;
import java.util.function.Function;

public final class LloydsAlternateModulusElevenCheck {
    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        if (!WeightRow.isExceptionTwoAndNine(Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))), Optional.ofNullable(
                WeightRow.copy(params.secondWeightRow.orElse(null))))) {
            return false;
        }

        BankAccount account = new BankAccount(BankAccount.LLOYDS_EURO_SORT_CODE, params.account.accountNumber);

        int total = ModulusTotal.calculate(account, selectedRow.getWeights());
        return total % 11 == 0;
    }
}
