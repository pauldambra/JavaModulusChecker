package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.SortCodeSubstitution;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

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
