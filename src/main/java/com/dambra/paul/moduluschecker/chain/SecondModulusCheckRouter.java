package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.chain.checks.DoubleAlternateCheck;
import com.dambra.paul.moduluschecker.chain.checks.ModulusElevenCheck;
import com.dambra.paul.moduluschecker.chain.checks.ModulusTenCheck;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public class SecondModulusCheckRouter implements ModulusChainCheck {
    private final DoubleAlternateCheck doubleAlternateCheck;
    private final ModulusTenCheck modulusTenCheck;
    private final ModulusElevenCheck modulusElevenCheck;

    public SecondModulusCheckRouter(
            DoubleAlternateCheck doubleAlternateCheck,
            ModulusTenCheck modulusTenCheck,
            ModulusElevenCheck modulusElevenCheck) {
        this.doubleAlternateCheck = doubleAlternateCheck;
        this.modulusTenCheck = modulusTenCheck;
        this.modulusElevenCheck = modulusElevenCheck;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        Boolean result = false;

        if (isExceptionTwoAndNineWithPassingFirstCheck(params)) {
            return params.getModulusResult().get();
        }

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.getSecondWeightRow().get();
        switch (params.getSecondWeightRow().get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = runOrSkip(params, rowSelector);
                break;
            case MOD10:
                result = modulusTenCheck.check(params, rowSelector);
                break;
            case MOD11:
                result = modulusElevenCheck.check(params, rowSelector);
                break;
        }

        return ModulusResult.WithSecondResult(params.getModulusResult(), result);
    }

    private Boolean runOrSkip(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        return canSkipForExceptionThree(params) ? null : doubleAlternateCheck.check(params, rowSelector);
    }

    private boolean canSkipForExceptionThree(ModulusCheckParams params) {
        int c = params.getAccount().getNumberAt(BankAccount.C);
        return c == 6 || c ==9;
    }

    private boolean isExceptionTwoAndNineWithPassingFirstCheck(ModulusCheckParams params) {
        boolean isExceptionTwoAndNine = WeightRow.isExceptionTwoAndNine(params.getFirstWeightRow(), params.getSecondWeightRow());
        boolean hasResults = params.getModulusResult().isPresent();
        boolean hasFirstCheckResult = params.getModulusResult().get().firstCheck.isPresent();
        boolean firstCheckSucceeded = params.getModulusResult().get().firstCheck.get();

        return isExceptionTwoAndNine && hasResults && hasFirstCheckResult && firstCheckSucceeded;
    }
}
