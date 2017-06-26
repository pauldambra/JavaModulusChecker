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

        return modulusResults.processResults();
    }

    private ModulusChainCheck makeModulusChainCheck() {
        SecondModulusCheckRouter secondModulusCheckRouter = new SecondModulusCheckRouter(sortCodeSubstitution);
        ExceptionFourteenGate exceptionFourteenGate = new ExceptionFourteenGate(secondModulusCheckRouter);
        ExceptionTwoGate exceptionTwoGate = new ExceptionTwoGate(exceptionFourteenGate);
        SecondCheckRequiredGate secondCheckRequiredGate = new SecondCheckRequiredGate(exceptionTwoGate);
        FirstModulusCheckRouter firstModulusCheckRouter = new FirstModulusCheckRouter(
                sortCodeSubstitution,
                secondCheckRequiredGate
        );
        ExceptionSixGate exceptionSixGate = new ExceptionSixGate(firstModulusCheckRouter);
        return new AtLeastOneWeightRowGate(weightRows, exceptionSixGate);
    }
}
