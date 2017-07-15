package com.github.pauldambra.moduluschecker.chain;

import com.github.pauldambra.moduluschecker.ModulusAlgorithm;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.SortCodeSubstitution;
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFiveDoubleAlternateCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusElevenCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.function.Function;

public class SecondModulusCheckRouter implements ModulusChainLink {

    private final SortCodeSubstitution sortCodeSubstitution;
    private static final Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.secondWeightRow.get();
    private static Map<ModulusAlgorithm, Function<ModulusCheckParams, Boolean>> checkAlgorithm;

    public SecondModulusCheckRouter(SortCodeSubstitution sortCodeSubstitution) {
        this.sortCodeSubstitution = sortCodeSubstitution;

        checkAlgorithm = ImmutableMap.<ModulusAlgorithm, Function<ModulusCheckParams, Boolean>>builder()
                .put(
                        ModulusAlgorithm.DOUBLE_ALTERNATE,
                        params -> WeightRow.isExceptionFive(params.secondWeightRow)
                                ? new ExceptionFiveDoubleAlternateCheck(sortCodeSubstitution).check(params, rowSelector)
                                : new DoubleAlternateCheck().check(params, rowSelector)
                )
                .put(
                        ModulusAlgorithm.MOD10,
                        params -> new ModulusTenCheck().check(params, rowSelector)
                )
                .put(
                     ModulusAlgorithm.MOD11,
                        params -> new ModulusElevenCheck().check(params, rowSelector)
                )
                .build();
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        boolean result = runModulusCheck(params);
        return updateResult(params, result);
    }

    private boolean runModulusCheck(ModulusCheckParams params) {
        final ModulusAlgorithm modulusAlgorithm = params.secondWeightRow.get().modulusAlgorithm;
        if (checkAlgorithm.containsKey(modulusAlgorithm)) {
            return checkAlgorithm.get(modulusAlgorithm).apply(params);
        }
        return false;
    }

    private ModulusResult updateResult(ModulusCheckParams params, boolean result) {
        return ModulusResult
                .withSecondResult(params.modulusResult, result)
                .withSecondException(params.secondWeightRow.flatMap(weightRow -> weightRow.exception));
    }

}
