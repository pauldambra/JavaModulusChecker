package com.dambra.paul.moduluschecker.chain.gates;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.chain.ModulusChainLink;
import com.dambra.paul.moduluschecker.chain.ModulusResult;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

public class ExceptionTwoGate implements ModulusChainLink {

    private final ExceptionFourteenGate next;

    public ExceptionTwoGate(ExceptionFourteenGate next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (hasNoSecondWeightRowToCheck(params) && isNotExceptionFourteen(params)) {
            return params.modulusResult.get();
        }

        if (WeightRow.isExceptionTwo(params.firstWeightRow) && params.firstCheckPassed()) {
            return params.modulusResult.get();
        }

        return next.check(params);

    }

    private boolean hasNoSecondWeightRowToCheck(ModulusCheckParams params) {
        return !params.secondWeightRow.isPresent();
    }

    private boolean isNotExceptionFourteen(ModulusCheckParams params) {
        return !WeightRow.isExceptionFourteen(params.firstWeightRow);
    }
}
