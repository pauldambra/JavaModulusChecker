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

        if (rowSelector.apply(params).isExceptionTen()) {
            //For the exception 10 check, if ab = 09 or ab = 99 and g = 9, zeroise weighting positions u-b.
            int a = params.getAccount().getNumberAt(BankAccount.A);
            int b = params.getAccount().getNumberAt(BankAccount.B);
            int g = params.getAccount().getNumberAt(BankAccount.G);
            if (a == 0 || a == 9
                && b == 9
                && g == 9) {
                BankAccount account = params.getAccount().zeroiseUToB();
                params = params.withAccount(account);
            }
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

        ModulusResult modulusResult = new ModulusResult(Optional.of(result), Optional.empty());
        modulusResult = wasExceptionFive(params, modulusResult);
        modulusResult = wasExceptionTen(params, modulusResult);

        return next.check(new ModulusCheckParams(
                params.getAccount(),
                params.getFirstWeightRow(),
                params.getSecondWeightRow(),
                Optional.of(modulusResult)
        ));
    }

    private ModulusResult wasExceptionFive(ModulusCheckParams params, ModulusResult modulusResult) {
        boolean isExceptionFive = params.getFirstWeightRow().get().isExceptionFive();
        if (isExceptionFive)
            modulusResult = ModulusResult.WasProcessedAsExceptionFive(modulusResult);
        return modulusResult;
    }

    private ModulusResult wasExceptionTen(ModulusCheckParams params, ModulusResult modulusResult) {
        boolean isExceptionTen = params.getFirstWeightRow().get().isExceptionTen();
        if (isExceptionTen)
            modulusResult = ModulusResult.WasProcessedAsExceptionTen(modulusResult);
        return modulusResult;
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
