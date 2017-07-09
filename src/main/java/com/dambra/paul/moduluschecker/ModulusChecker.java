package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.chain.*;
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
        return modulusCheckingChain()
                .check(params)
                .processResults();
    }

    private ModulusChainCheck modulusCheckingChain() {
        final SecondModulusCheckRouter secondModulusCheckRouter = new SecondModulusCheckRouter(sortCodeSubstitution);
        final ExceptionFourteenGate exceptionFourteenGate = new ExceptionFourteenGate(secondModulusCheckRouter);
        final ExceptionTwoGate exceptionTwoGate = new ExceptionTwoGate(exceptionFourteenGate);
        final SecondCheckRequiredGate secondCheckRequiredGate = new SecondCheckRequiredGate(exceptionTwoGate);
        final FirstModulusCheckRouter firstModulusCheckRouter = new FirstModulusCheckRouter(
                sortCodeSubstitution,
                secondCheckRequiredGate
        );
        final ExceptionSixGate exceptionSixGate = new ExceptionSixGate(firstModulusCheckRouter);
        return new AtLeastOneWeightRowGate(weightRows, exceptionSixGate);
    }
}
