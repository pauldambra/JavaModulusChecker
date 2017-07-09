package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

public final class ExceptionTwoAndNineGate implements ModulusChainLink {

    private final ExceptionSevenAccountTransformer next;

    public ExceptionTwoAndNineGate(ExceptionSevenAccountTransformer next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (isExceptionTwoAndNine(params)
                &&firstCheckSucceeded(params)) {
            return params.modulusResult.get();
        }
        return next.check(params);
    }

    private boolean isExceptionTwoAndNine(ModulusCheckParams params) {
        return WeightRow.isExceptionTwoAndNine(
                params.firstWeightRow,
                params.secondWeightRow);
    }

    private boolean firstCheckSucceeded(ModulusCheckParams params) {
        return params.modulusResult.isPresent()
                && params.modulusResult.get().firstCheckResult.isPresent()
                && params.modulusResult.get().firstCheckResult.get();
    }
}
