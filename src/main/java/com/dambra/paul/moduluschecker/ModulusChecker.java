package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.chain.SortCodeSubstitutionCheck;

import java.util.Optional;

public class ModulusChecker {
    public Boolean checkBankAccount(String sortcode, String accountNumber) {

        Optional<SortCodeSubstitution> sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt");

        ModulusWeightRows.fromFile("file/valacdos.txt");

        BankAccount account = new BankAccount(sortcode, accountNumber);
        //ModulusCheckParams startingParams = new ModulusCheckParams(account, We)

        return new SortCodeSubstitutionCheck(sortCodeSubstitution.get(), null).check(null);
    }
}
