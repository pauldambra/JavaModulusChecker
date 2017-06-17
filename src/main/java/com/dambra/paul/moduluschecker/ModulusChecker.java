package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.chain.*;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;

import java.util.Optional;

public class ModulusChecker {
    Optional<SortCodeSubstitution> sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt");

    Optional<ModulusWeightRows> weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

    public Boolean checkBankAccount(String sortCode, String accountNumber) {

        BankAccount account = new BankAccount(sortCode, accountNumber);

        ModulusCheckParams startingParams = new ModulusCheckParams(account, Optional.empty(), Optional.empty(), Optional.empty());

        ModulusChainCheck chain = makeModulusChainCheck();

        ModulusResult modulusResults = chain.check(startingParams);

        Boolean firstResult = modulusResults.firstCheck.orElse(false);
        Boolean secondResult = modulusResults.secondCheck.orElse(false);
        return firstResult || secondResult;
    }

    private ModulusChainCheck makeModulusChainCheck() {
        SecondModulusCheckRouter secondModulusCheckRouter = new SecondModulusCheckRouter(
                new DoubleAlternateCheck(),
                new ModulusTenCheck(),
                new ModulusElevenCheck()
        );
        SecondModulusCheckGate secondModulusCheckGate = new SecondModulusCheckGate(secondModulusCheckRouter);
        FirstModulusCheckRouter firstModulusCheckRouter = new FirstModulusCheckRouter(
                new DoubleAlternateCheck(),
                new ModulusTenCheck(),
                new ModulusElevenCheck(),
                secondModulusCheckGate
        );
        AtLeastOneWeightRowGate atLeastOneWeightRowGate = new AtLeastOneWeightRowGate(weightRows, firstModulusCheckRouter);
        return new SortCodeSubstitutionCheck(sortCodeSubstitution.get(), atLeastOneWeightRowGate);
    }
}
