package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;

public final class ExceptionThreeGate implements ModulusChainLink {

    private final SecondModulusCheckRouter next;

    public ExceptionThreeGate(SecondModulusCheckRouter next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (canSkipForExceptionThree(params)) {
           return ModulusResult.withSecondResult(params.modulusResult, null);
        }
        return next.check(params);
    }

    private boolean canSkipForExceptionThree(ModulusCheckParams params) {
        int c = params.account.getNumberAt(BankAccount.C);
        return c == 6 || c ==9;
    }
}
