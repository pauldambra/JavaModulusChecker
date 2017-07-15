package com.github.pauldambra.moduluschecker.chain;

import com.github.pauldambra.moduluschecker.ModulusAlgorithm;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.SortCodeSubstitution;
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFiveModulusElevenCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusElevenCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck;
import com.github.pauldambra.moduluschecker.chain.gates.SecondCheckRequiredGate;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.function.Function;

public final class FirstModulusCheckRouter implements ModulusChainLink {
    private final SecondCheckRequiredGate next;

    private static final Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.firstWeightRow.get();
    private static Map<ModulusAlgorithm, Function<ModulusCheckParams, Boolean>> checkAlgorithm;

    public FirstModulusCheckRouter(
            SortCodeSubstitution sortCodeSubstitution,
            SecondCheckRequiredGate exceptionTwoGate) {
        this.next = exceptionTwoGate;

        checkAlgorithm = ImmutableMap.<ModulusAlgorithm, Function<ModulusCheckParams, Boolean>>builder()
                .put(
                        ModulusAlgorithm.DOUBLE_ALTERNATE,
                        params -> new DoubleAlternateCheck().check(params, rowSelector))
                .put(
                        ModulusAlgorithm.MOD10,
                        params -> new ModulusTenCheck().check(params, rowSelector))
                .put(
                        ModulusAlgorithm.MOD11,
                        params -> WeightRow.isExceptionFive(params.firstWeightRow)
                                ? new ExceptionFiveModulusElevenCheck(sortCodeSubstitution).check(params, rowSelector)
                                : new ModulusElevenCheck().check(params, rowSelector))
                .build();
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        boolean result = runModulusCheck(params);
        final ModulusCheckParams updatedParams = updateParamsWithResult(params, result);
        return next.check(updatedParams);
    }

    private boolean runModulusCheck(ModulusCheckParams params) {
        final ModulusAlgorithm modulusAlgorithm = params.firstWeightRow.get().modulusAlgorithm;
        if (checkAlgorithm.containsKey(modulusAlgorithm)) {
            return checkAlgorithm.get(modulusAlgorithm).apply(params);
        }
        return false;
    }

    private ModulusCheckParams updateParamsWithResult(ModulusCheckParams params, boolean result) {
        final ModulusResult modulusResult =
                ModulusResult
                        .WithFirstResult(result)
                        .withFirstException(params.firstWeightRow.flatMap(weightRow -> weightRow.exception));

        return params.withResult(modulusResult);
    }

}
