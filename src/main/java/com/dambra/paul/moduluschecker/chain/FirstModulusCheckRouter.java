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
    private final SecondCheckRequiredGate next;

    public FirstModulusCheckRouter(
            SortCodeSubstitution sortCodeSubstitution,
            SecondCheckRequiredGate exceptionTwoGate) {
        this.sortCodeSubstitution = sortCodeSubstitution;
        this.next = exceptionTwoGate;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        boolean result = false;

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> Optional.ofNullable(
                WeightRow.copy(p.firstWeightRow.orElse(null))).get();

        if (rowSelector.apply(params).isException(7)) {
            BankAccount account = params.account.zeroiseUToB();
            params = params.withAccount(account);
        }

        if (rowSelector.apply(params).isException(8)) {
            BankAccount account = new BankAccount("090126", params.account.accountNumber);
            params = params.withAccount(account);
        }

        if (rowSelector.apply(params).isException(10)) {
            //For the exception 10 check, if ab = 09 or ab = 99 and g = 9, zeroise weighting positions u-b.
            int a = params.account.getNumberAt(BankAccount.A);
            int b = params.account.getNumberAt(BankAccount.B);
            int g = params.account.getNumberAt(BankAccount.G);
            if (a == 0 || a == 9
                && b == 9
                && g == 9) {
                BankAccount account = params.account.zeroiseUToB();
                params = params.withAccount(account);
            }
        }

        switch (Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))).get().modulusAlgorithm) {
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
        modulusResult = modulusResult.withFirstException(
                Optional.ofNullable(
                        WeightRow.copy(params.firstWeightRow.orElse(null))).flatMap(weightRow -> weightRow.exception)
        );

        final ModulusCheckParams nextCheckParams = new ModulusCheckParams(
                params.account,
                Optional.ofNullable(
                        WeightRow.copy(params.firstWeightRow.orElse(null))),
                Optional.ofNullable(
                        WeightRow.copy(params.secondWeightRow.orElse(null))),
                Optional.of(modulusResult)
        );

        return next.check(nextCheckParams);
    }

    private boolean runStandardOrExceptionFiveCheck(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        boolean isExceptionFive = Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))).get().isException(5);

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
