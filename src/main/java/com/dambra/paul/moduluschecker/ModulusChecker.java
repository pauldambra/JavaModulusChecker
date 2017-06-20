package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.chain.*;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;

import java.io.IOException;
import java.util.Optional;

public class ModulusChecker {
    SortCodeSubstitution sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt");

    ModulusWeightRows weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

    public ModulusChecker() throws IOException {
    }

    public Boolean checkBankAccount(String sortCode, String accountNumber) {

        BankAccount account = new BankAccount(sortCode, accountNumber);

        ModulusCheckParams startingParams = new ModulusCheckParams(account, Optional.empty(), Optional.empty(), Optional.empty());

        ModulusChainCheck chain = makeModulusChainCheck();

        ModulusResult modulusResults = chain.check(startingParams);

        return processResults(modulusResults);
    }

    private Boolean processResults(ModulusResult modulusResults) {
        if (modulusResults.isExceptionFive) {
            return modulusResults.firstCheck.orElse(false)
                    && modulusResults.secondCheck.orElse(false);
        }

        if (modulusResults.isExceptionTen) {
            return modulusResults.firstCheck.orElse(false)
                    || modulusResults.secondCheck.orElse(false);
        }

        if (modulusResults.secondCheck.isPresent()) {
            return modulusResults.secondCheck.get();
        }

        return modulusResults.firstCheck.orElse(false);
    }

    private ModulusChainCheck makeModulusChainCheck() {
        SecondModulusCheckRouter secondModulusCheckRouter = new SecondModulusCheckRouter(sortCodeSubstitution);
        SecondModulusCheckGate secondModulusCheckGate = new SecondModulusCheckGate(secondModulusCheckRouter);
        FirstModulusCheckRouter firstModulusCheckRouter = new FirstModulusCheckRouter(
                sortCodeSubstitution,
                secondModulusCheckGate
        );
        ExceptionSixGate exceptionSixGate = new ExceptionSixGate(firstModulusCheckRouter);
        return new AtLeastOneWeightRowGate(weightRows, exceptionSixGate);
    }
}
