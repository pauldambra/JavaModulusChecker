package com.dambra.paul.moduluschecker.chain.gates;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.chain.ModulusChainLink;
import com.dambra.paul.moduluschecker.chain.ModulusResult;
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

        if (params.secondWeightRow.isPresent()) {
            return next.check(params);
        }

        return params.modulusResult.orElse(ModulusResult.PASSES);
    }

    private boolean exceptionRequiresSecondCheck(ModulusCheckParams params) {
        return rowExceptionRequiresSecondCheck(params.firstWeightRow)
                || rowExceptionRequiresSecondCheck(params.secondWeightRow);
    }

    private boolean rowExceptionRequiresSecondCheck(Optional<WeightRow> row) {
        return row.isPresent()
                && ModulusResult.exceptionsThatRequireSecondCheck.contains(
                row.get().exception.orElse(-1));
    }
}
