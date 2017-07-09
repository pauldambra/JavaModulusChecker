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

        boolean result = false;

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.secondWeightRow.get();

        switch (rowSelector.apply(params).modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = runStandardOrExceptionFiveCheck(params, rowSelector);
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

    private Boolean runStandardOrExceptionFiveCheck(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        if (params.secondWeightRow.get().isException(5)) {
            return new ExceptionFiveDoubleAlternateCheck(sortCodeSubstitution).check(params, rowSelector);
        }
        return new DoubleAlternateCheck().check(params, rowSelector);
    }

    private Boolean runStandardOrExceptionFourteenCheck(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        return new ModulusElevenCheck().check(params, rowSelector);
    }
}
