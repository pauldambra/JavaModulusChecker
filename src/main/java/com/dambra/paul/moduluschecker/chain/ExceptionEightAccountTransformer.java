package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;

public final class ExceptionEightAccountTransformer implements ModulusChainLink {

    private final ExceptionTenAccountTransformer next;

    public ExceptionEightAccountTransformer(ExceptionTenAccountTransformer next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (params.firstWeightRow.get().isException(8)) {
            BankAccount account = BankAccount.Of("090126", params.account.accountNumber);
            params = params.withAccount(account);
        }
        return next.check(params);
    }
}
