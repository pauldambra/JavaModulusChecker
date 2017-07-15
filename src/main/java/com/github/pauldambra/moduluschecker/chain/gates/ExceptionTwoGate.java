package com.github.pauldambra.moduluschecker.chain.gates;

import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink;
import com.github.pauldambra.moduluschecker.chain.ModulusResult;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

public class ExceptionTwoGate implements ModulusChainLink {

    private final ExceptionFourteenGate next;

    public ExceptionTwoGate(ExceptionFourteenGate next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (WeightRow.isExceptionTwo(params.firstWeightRow)
                && ModulusResult.firstCheckPassed(params.modulusResult)) {
            return params.modulusResult.get();
        }

        return next.check(params);

    }
}
