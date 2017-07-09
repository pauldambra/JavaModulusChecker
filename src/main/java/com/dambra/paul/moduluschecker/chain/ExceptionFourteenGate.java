package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;

/**
 * Exception Fourteen
 * Perform the modulus 11 check as normal:
 * • If the check passes (that is, there is no remainder), then the account number should be
 * considered valid. Do not perform the second check
 */
public class ExceptionFourteenGate implements ModulusChainLink {

    private final ExceptionTwoAndNineGate next;

    public ExceptionFourteenGate(ExceptionTwoAndNineGate next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        final boolean firstCheckResult = params.firstCheckPassed();
        return WeightRow.isExceptionFourteen(params.firstWeightRow) && firstCheckResult
            ? new ModulusResult(Optional.of(true), Optional.empty())
            : next.check(params);
    }

}
