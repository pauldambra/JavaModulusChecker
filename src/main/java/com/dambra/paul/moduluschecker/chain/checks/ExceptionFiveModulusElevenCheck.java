package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.SortCodeSubstitution;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

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