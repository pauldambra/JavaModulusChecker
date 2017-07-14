package com.github.pauldambra.moduluschecker.chain;

import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.SortCodeSubstitution;
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFiveModulusElevenCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusElevenCheck;
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck;
import com.github.pauldambra.moduluschecker.chain.gates.SecondCheckRequiredGate;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public final class FirstModulusCheckRouter implements ModulusChainLink {
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

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.firstWeightRow.get();

        switch (params.firstWeightRow.get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = new DoubleAlternateCheck().check(params, rowSelector);
                break;
            case MOD10:
                result = new ModulusTenCheck().check(params, rowSelector);
                break;
            case MOD11:
                result = WeightRow.isExceptionFive(params.firstWeightRow)
                        ? new ExceptionFiveModulusElevenCheck(sortCodeSubstitution).check(params, rowSelector)
                        : new ModulusElevenCheck().check(params, rowSelector);
                break;
        }

        final ModulusResult modulusResult = ModulusResult
                    .WithFirstResult(result)
                    .withFirstException(params.firstWeightRow.flatMap(weightRow -> weightRow.exception));

        return next.check(params.withResult(modulusResult));
    }

}
