package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;

public class ExceptionTwoGate implements ModulusChainCheck {

    private final ExceptionFourteenGate next;

    public ExceptionTwoGate(ExceptionFourteenGate next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (hasNoSecondWeightRowToCheck(params) && isNotExceptionFourteen(params)) {
            return Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).get();
        }

        if (Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))).get().isException(2)) {
            if (Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).get().firstCheckResult.get()) {
                return Optional.ofNullable(ModulusResult.copy(params.modulusResult.orElse(null))).get();
            }
        }

        return next.check(params);
    }

    private boolean hasNoSecondWeightRowToCheck(ModulusCheckParams params) {
        return !Optional.ofNullable(
                WeightRow.copy(params.secondWeightRow.orElse(null))).isPresent();
    }

    private boolean isNotExceptionFourteen(ModulusCheckParams params) {
        return !Optional.ofNullable(
                WeightRow.copy(params.firstWeightRow.orElse(null))).get().isException(14);
    }
}
