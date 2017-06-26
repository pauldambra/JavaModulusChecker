package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;

public class SecondCheckRequiredGate implements  ModulusChainCheck {

    private final ExceptionTwoGate next;

    public SecondCheckRequiredGate(ExceptionTwoGate next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        System.out.println(params);

        if (exceptionRequiresSecondCheck(params)) {
            System.out.println("running next check");
            return next.check(params);
        }

        if (params.getSecondWeightRow().isPresent()) {
            System.out.println("running second check");
            return next.check(params);
        }

        System.out.println("not running next check");
        return params.getModulusResult().orElse(ModulusResult.PASSES);
    }

    private boolean exceptionRequiresSecondCheck(ModulusCheckParams params) {
        final boolean firstMatchExceptionRequiresSecondCheck =
                params.getFirstWeightRow().isPresent()
                && ModulusResult.exceptionsThatRequireSecondCheck.contains(params.getFirstWeightRow().get().exception.orElse(-1));

        final boolean secondMatchExceptionRequiresSecondCheck =
                params.getSecondWeightRow().isPresent()
                && ModulusResult.exceptionsThatRequireSecondCheck.contains(params.getSecondWeightRow().get().exception.orElse(-1));

        return firstMatchExceptionRequiresSecondCheck
                || secondMatchExceptionRequiresSecondCheck;
    }
}
