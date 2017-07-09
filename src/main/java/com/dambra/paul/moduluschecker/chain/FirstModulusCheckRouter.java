package com.dambra.paul.moduluschecker.chain;

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
