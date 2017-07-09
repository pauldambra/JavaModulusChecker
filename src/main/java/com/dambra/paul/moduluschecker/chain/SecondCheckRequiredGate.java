package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;

public class SecondCheckRequiredGate implements ModulusChainLink {

    private final ExceptionTwoGate next;

    public SecondCheckRequiredGate(ExceptionTwoGate next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        if (exceptionRequiresSecondCheck(params)) {
            return next.check(params);
        }

        if (Optional.ofNullable(
                WeightRow.copy(params.secondWeightRow.orElse(null))).isPresent()) {
            return next.check(params);
        }

        return Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).orElse(ModulusResult.PASSES);
    }

    private boolean exceptionRequiresSecondCheck(ModulusCheckParams params) {
        final boolean firstMatchExceptionRequiresSecondCheck =
                Optional.ofNullable(
                        WeightRow.copy(params.firstWeightRow.orElse(null))).isPresent()
                && ModulusResult.exceptionsThatRequireSecondCheck.contains(Optional.ofNullable(
                        WeightRow.copy(params.firstWeightRow.orElse(null))).get().exception.orElse(-1));

        final boolean secondMatchExceptionRequiresSecondCheck =
                Optional.ofNullable(
                        WeightRow.copy(params.secondWeightRow.orElse(null))).isPresent()
                && ModulusResult.exceptionsThatRequireSecondCheck.contains(Optional.ofNullable(
                        WeightRow.copy(params.secondWeightRow.orElse(null))).get().exception.orElse(-1));

        return firstMatchExceptionRequiresSecondCheck
                || secondMatchExceptionRequiresSecondCheck;
    }
}
