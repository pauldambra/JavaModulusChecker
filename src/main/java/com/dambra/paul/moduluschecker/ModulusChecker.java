package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.chain.*;

import java.util.Optional;

public class ModulusChecker {
    public Boolean checkBankAccount(String sortcode, String accountNumber) {

        Optional<SortCodeSubstitution> sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt");

        Optional<ModulusWeightRows> weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

        BankAccount account = new BankAccount(sortcode, accountNumber);

        ModulusCheckParams startingParams =
                new ModulusCheckParams(account, Optional.empty(), Optional.empty());

        return new SortCodeSubstitutionCheck(
                    sortCodeSubstitution.get(),
                    new AtLeastOneWeightRowGate(weightRows, new FirstModulusCheckRouter(
                            new DoubleAlternateCheck(),
                            new ModulusTenCheck(),
                            new ModulusElevenCheck()
                    )))
                .check(startingParams);
    }
}
