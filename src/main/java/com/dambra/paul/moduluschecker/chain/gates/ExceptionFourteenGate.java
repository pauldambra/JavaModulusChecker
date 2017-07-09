package com.dambra.paul.moduluschecker.chain.gates;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.chain.ModulusChainLink;
import com.dambra.paul.moduluschecker.chain.ModulusResult;
import com.dambra.paul.moduluschecker.chain.checks.ExceptionFourteenModulusElevenCheck;
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

        if (!WeightRow.isExceptionFourteen(params.firstWeightRow)) {
            return next.check(params);
        }

        return params.firstCheckPassed()
            ? new ModulusResult(Optional.of(true), Optional.empty())
            : ModulusResult.withSecondResult(
                    params.modulusResult,
                    new ExceptionFourteenModulusElevenCheck().check(params));
    }

}
