package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.SortCodeSubstitution;
import com.dambra.paul.moduluschecker.chain.checks.*;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public class SecondModulusCheckRouter implements ModulusChainLink {

    private final SortCodeSubstitution sortCodeSubstitution;

    public SecondModulusCheckRouter(SortCodeSubstitution sortCodeSubstitution) {
        this.sortCodeSubstitution = sortCodeSubstitution;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        Boolean result = false;

        if (WeightRow.isExceptionFourteen(params.firstWeightRow)) {
            final Boolean secondCheckResult = new ExceptionFourteenModulusElevenCheck().check(params);
            return ModulusResult.withSecondResult(params.modulusResult, secondCheckResult);
        }

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.secondWeightRow.get();

        if (rowSelector.apply(params).isException(7)) {
            BankAccount account = params.account.zeroiseUToB();
            params = params.withAccount(account);
        }

        if (rowSelector.apply(params).isException(8)) {
            BankAccount account = BankAccount.Of("090126", params.account.accountNumber);
            params = params.withAccount(account);
        }

        switch (params.secondWeightRow.get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = runOrSkip(params, rowSelector);
                break;
            case MOD10:
                result = new ModulusTenCheck().check(params, rowSelector);
                break;
            case MOD11:
                result = runStandardOrExceptionFourteenCheck(params, rowSelector);
                break;
        }

        final ModulusResult modulusResult = ModulusResult.withSecondResult(params.modulusResult, result);
        return modulusResult.withSecondException(
                params.secondWeightRow.flatMap(weightRow -> weightRow.exception)
        );
    }

    private Boolean runOrSkip(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        return canSkipForExceptionThree(params) ? null : runStandardOrExceptionFiveCheck(params, rowSelector);
    }

    private Boolean runStandardOrExceptionFiveCheck(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        if (params.secondWeightRow.get().isException(5)) {
            return new ExceptionFiveDoubleAlternateCheck(sortCodeSubstitution).check(params, rowSelector);
        }
        return new DoubleAlternateCheck().check(params, rowSelector);
    }

    private boolean canSkipForExceptionThree(ModulusCheckParams params) {
        int c = params.account.getNumberAt(BankAccount.C);
        return c == 6 || c ==9;
    }

    private Boolean runStandardOrExceptionFourteenCheck(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        return new ModulusElevenCheck().check(params, rowSelector);
    }
}
