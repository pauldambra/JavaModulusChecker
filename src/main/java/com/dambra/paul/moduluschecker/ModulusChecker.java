package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;

import java.io.IOException;

public class ModulusChecker {
    private SortCodeSubstitution sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt");

    private ModulusWeightRows weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

    public ModulusChecker() throws IOException {
    }

    public Boolean checkBankAccount(String sortCode, String accountNumber) {
        final ModulusCheckParams params = ModulusCheckParams.startingParams(
                BankAccount.Of(sortCode, accountNumber));
        return ModulusCheckingChain
                .create(weightRows, sortCodeSubstitution)
                .check(params)
                .processResults();
    }

}
