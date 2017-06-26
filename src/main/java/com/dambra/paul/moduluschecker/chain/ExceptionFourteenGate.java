package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;

import java.util.Optional;

/**
 * Exception Fourteen
 * Perform the modulus 11 check as normal:
 * • If the check passes (that is, there is no remainder), then the account number should be
 * considered valid. Do not perform the second check
 */
public class ExceptionFourteenGate implements ModulusChainCheck {

    private final SecondModulusCheckRouter next;

    public ExceptionFourteenGate(SecondModulusCheckRouter next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        if(isExceptionFourteen(params) && firstCheckPassed(params)) {
            return new ModulusResult(params.getModulusResult().get().firstCheckResult, Optional.empty());
        }
        return next.check(params);
    }

    private boolean isExceptionFourteen(ModulusCheckParams params) {
        return params.getFirstWeightRow().isPresent() && params.getFirstWeightRow().get().isExceptionFourteen();
    }

    private boolean firstCheckPassed(ModulusCheckParams params) {
        return params.getModulusResult().isPresent() && params.getModulusResult().get().firstCheckResult.orElse(false);
    }
}
