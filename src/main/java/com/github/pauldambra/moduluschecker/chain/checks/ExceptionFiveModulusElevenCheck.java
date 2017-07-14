package com.github.pauldambra.moduluschecker.chain.checks;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.SortCodeSubstitution;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public final class ExceptionFiveModulusElevenCheck {

    private final SortCodeSubstitution sortCodeSubstitution;

    public ExceptionFiveModulusElevenCheck(SortCodeSubstitution sortCodeSubstitution) {
        this.sortCodeSubstitution = sortCodeSubstitution;
    }

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        BankAccount bankAccount = sortCodeSubstitution.Apply(params.account);
        params = params.withAccount(bankAccount);

        int total = ModulusTotal.calculate(params.account, selectedRow.getWeights());
        int remainder = total % 11;

        int checkDigit = params.account.getNumberAt(BankAccount.G);

        if (remainder == 0 && checkDigit == 0) {
            return true;
        }

        if (remainder == 1) {
            return false;
        }

        return (11 - remainder) == checkDigit;
    }
}
