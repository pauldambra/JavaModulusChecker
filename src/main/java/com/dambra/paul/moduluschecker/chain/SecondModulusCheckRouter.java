package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

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

        boolean result = false;

        if (isExceptionTwoAndNineWithPassingFirstCheck(params)) {
            return params.getModulusResult().get();
        }


        if (isExceptionTwoAndNineWithFailingFirstCheck(params)) {
            params = new ModulusCheckParams(
                    new BankAccount(BankAccount.LLOYDS_EURO_SORT_CODE, params.getAccount().accountNumber),
                    params.getFirstWeightRow(),
                    params.getSecondWeightRow(),
                    params.getModulusResult()
            );
        }

        switch (params.getSecondWeightRow().get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = doubleAlternateCheck.check(params, p -> p.getSecondWeightRow().get());
                break;
            case MOD10:
                result = modulusTenCheck.check(params, p -> p.getSecondWeightRow().get());
                break;
            case MOD11:
                result = modulusElevenCheck.check(params, p -> p.getSecondWeightRow().get());
                break;
        }

        return ModulusResult.WithSecondResult(params.getModulusResult(), result);
    }

    private boolean isExceptionTwoAndNineWithPassingFirstCheck(ModulusCheckParams params) {
        boolean isExceptionTwoAndNine = WeightRow.isExceptionTwoAndNine(params.getFirstWeightRow(), params.getSecondWeightRow());
        boolean hasResults = params.getModulusResult().isPresent();
        boolean hasFirstCheckResult = params.getModulusResult().get().firstCheck.isPresent();
        boolean firstCheckSucceeded = params.getModulusResult().get().firstCheck.get();

        return isExceptionTwoAndNine && hasResults && hasFirstCheckResult && firstCheckSucceeded;
    }

    private boolean isExceptionTwoAndNineWithFailingFirstCheck(ModulusCheckParams params) {
        return WeightRow.isExceptionTwoAndNine(params.getFirstWeightRow(), params.getSecondWeightRow())
                && params.getModulusResult().isPresent()
                && params.getModulusResult().get().firstCheck.isPresent()
                && !params.getModulusResult().get().firstCheck.get();
    }
}
