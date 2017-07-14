package com.github.pauldambra.moduluschecker.chain;

import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.SortCodeSubstitution;
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFiveDoubleAlternateCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusElevenCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

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
                result = WeightRow.isExceptionFive(params.secondWeightRow)
                        ? new ExceptionFiveDoubleAlternateCheck(sortCodeSubstitution).check(params, rowSelector)
                        : new DoubleAlternateCheck().check(params, rowSelector);
                break;
            case MOD10:
                result = new ModulusTenCheck().check(params, rowSelector);
                break;
            case MOD11:
                result = new ModulusElevenCheck().check(params, rowSelector);
                break;
        }

        final ModulusResult modulusResult = ModulusResult.withSecondResult(params.modulusResult, result);
        return modulusResult.withSecondException(
                params.secondWeightRow.flatMap(weightRow -> weightRow.exception)
        );
    }

}
