package com.github.pauldambra.moduluschecker.chain.gates;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink;
import com.github.pauldambra.moduluschecker.chain.ModulusResult;
import com.github.pauldambra.moduluschecker.chain.SecondModulusCheckRouter;

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
