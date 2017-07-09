package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.SortCodeSubstitution;
import com.dambra.paul.moduluschecker.chain.checks.*;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;
import java.util.function.Function;

public class SecondModulusCheckRouter implements ModulusChainCheck {

    private final SortCodeSubstitution sortCodeSubstitution;

    public SecondModulusCheckRouter(SortCodeSubstitution sortCodeSubstitution) {
        this.sortCodeSubstitution = sortCodeSubstitution;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        Boolean result = false;

        if (Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))).get().isException(14)) {
            final Boolean secondCheckResult = new ExceptionFourteenModulusElevenCheck().check(params);
            return ModulusResult.withSecondResult(Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))), secondCheckResult);
        }

        if (isExceptionTwoAndNineWithPassingFirstCheck(params)) {
            return Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).get();
        }

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> Optional.ofNullable(
                WeightRow.copy(p.secondWeightRow.orElse(null))).get();

        if (rowSelector.apply(params).isException(7)) {
            BankAccount account = params.account.zeroiseUToB();
            params = params.withAccount(account);
        }

        if (rowSelector.apply(params).isException(8)) {
            BankAccount account = BankAccount.Of("090126", params.account.accountNumber);
            params = params.withAccount(account);
        }

        switch (Optional.ofNullable(
                WeightRow.copy(params.secondWeightRow.orElse(null))).get().modulusAlgorithm) {
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

        final ModulusResult modulusResult = ModulusResult.withSecondResult(Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))), result);
        return modulusResult.withSecondException(
                Optional.ofNullable(
                        WeightRow.copy(params.secondWeightRow.orElse(null))).flatMap(weightRow -> weightRow.exception)
        );
    }

    private Boolean runOrSkip(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        return canSkipForExceptionThree(params) ? null : runStandardOrExceptionFiveCheck(params, rowSelector);
    }

    private Boolean runStandardOrExceptionFiveCheck(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        if (Optional.ofNullable(
                WeightRow.copy(params.secondWeightRow.orElse(null))).get().isException(5)) {
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

    private boolean isExceptionTwoAndNineWithPassingFirstCheck(ModulusCheckParams params) {
        boolean isExceptionTwoAndNine = WeightRow.isExceptionTwoAndNine(Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))), Optional.ofNullable(
                WeightRow.copy(params.secondWeightRow.orElse(null))));
        boolean hasResults = Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).isPresent();
        boolean hasFirstCheckResult = Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).get().firstCheckResult.isPresent();
        boolean firstCheckSucceeded = Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).get().firstCheckResult.get();

        return isExceptionTwoAndNine && hasResults && hasFirstCheckResult && firstCheckSucceeded;
    }
}
