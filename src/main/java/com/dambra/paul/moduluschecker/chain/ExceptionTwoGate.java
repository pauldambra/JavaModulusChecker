package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;

public class ExceptionTwoGate implements ModulusChainCheck {

    private final ExceptionFourteenGate next;

    public ExceptionTwoGate(ExceptionFourteenGate next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (hasNoSecondWeightRowToCheck(params) && isNotExceptionFourteen(params)) {
            return params.getModulusResult().get();
        }

        if (params.getFirstWeightRow().get().isExceptionTwo()) {
            if (params.getModulusResult().get().firstCheckResult.get()) {
                return params.getModulusResult().get();
            }
        }

        return next.check(params);
    }

    private boolean hasNoSecondWeightRowToCheck(ModulusCheckParams params) {
        return !params.getSecondWeightRow().isPresent();
    }

    private boolean isNotExceptionFourteen(ModulusCheckParams params) {
        return !params.getFirstWeightRow().get().isExceptionFourteen();
    }
}
