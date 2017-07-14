package com.github.pauldambra.moduluschecker.chain.checks;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.SortCodeSubstitution;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public final class ExceptionFiveDoubleAlternateCheck {

    private final SortCodeSubstitution sortCodeSubstitution;

    public ExceptionFiveDoubleAlternateCheck(SortCodeSubstitution sortCodeSubstitution) {
        this.sortCodeSubstitution = sortCodeSubstitution;
    }

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        BankAccount bankAccount = sortCodeSubstitution.Apply(params.account);
        params = params.withAccount(bankAccount);

        int total = ModulusTotal.calculateDoubleAlternate(params.account, selectedRow.getWeights());
        final int remainder = total % 10;
        final int checkDigit = params.account.getNumberAt(BankAccount.H);

        if (remainder == 0 && checkDigit == 0) {
            return true;
        }

        return (10 - remainder) == checkDigit;
    }
}
