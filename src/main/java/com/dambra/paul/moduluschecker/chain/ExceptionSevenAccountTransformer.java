package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;

public final class ExceptionSevenAccountTransformer implements ModulusChainCheck {

    private final FirstModulusCheckRouter next;

    public ExceptionSevenAccountTransformer(FirstModulusCheckRouter next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (params.firstWeightRow.get().isException(7)) {
            BankAccount account = params.account.zeroiseUToB();
            params = params.withAccount(account);
        }
        return next.check(params);
    }
}
