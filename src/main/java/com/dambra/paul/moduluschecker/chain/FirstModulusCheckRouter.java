package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.SortCodeSubstitution;
import com.dambra.paul.moduluschecker.chain.checks.DoubleAlternateCheck;
import com.dambra.paul.moduluschecker.chain.checks.ExceptionFiveModulusElevenCheck;
import com.dambra.paul.moduluschecker.chain.checks.ModulusElevenCheck;
import com.dambra.paul.moduluschecker.chain.checks.ModulusTenCheck;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;
import java.util.function.Function;


public final class FirstModulusCheckRouter implements ModulusChainCheck {
    private final SortCodeSubstitution sortCodeSubstitution;
    private final SecondModulusCheckGate next;

    public FirstModulusCheckRouter(
            SortCodeSubstitution sortCodeSubstitution,
            SecondModulusCheckGate secondModulusCheckGate) {
        this.sortCodeSubstitution = sortCodeSubstitution;
        this.next = secondModulusCheckGate;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        boolean result = false;

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.getFirstWeightRow().get();

        if (rowSelector.apply(params).isExceptionSeven()) {
            BankAccount account = params.getAccount().zeroiseUToB();
            params = params.withAccount(account);
        }

        if (rowSelector.apply(params).isExceptionEight()) {
            BankAccount account = new BankAccount("090126", params.getAccount().accountNumber);
            params = params.withAccount(account);
        }

        switch (params.getFirstWeightRow().get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = new DoubleAlternateCheck().check(params, rowSelector);
                break;
            case MOD10:
                result = new ModulusTenCheck().check(params, rowSelector);
                break;
            case MOD11:
                result = runStandardOrExceptionFiveCheck(params, rowSelector);
                break;
        }

        boolean isExceptionFive = params.getFirstWeightRow().get().isExceptionFive();
        ModulusResult modulusResult = new ModulusResult(Optional.of(result), Optional.empty());
        if (isExceptionFive)
            modulusResult = ModulusResult.WasProcessedAsExceptionFive(modulusResult);

        return next.check(new ModulusCheckParams(
                params.getAccount(),
                params.getFirstWeightRow(),
                params.getSecondWeightRow(),
                Optional.of(modulusResult)
        ));
    }

    private boolean runStandardOrExceptionFiveCheck(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        boolean isExceptionFive = params.getFirstWeightRow().get().isExceptionFive();

        if (isExceptionFive)
        {
            return new ExceptionFiveModulusElevenCheck(sortCodeSubstitution).check(params, rowSelector);
        }
        else
        {
            return new ModulusElevenCheck().check(params, rowSelector);
        }
    }
}
