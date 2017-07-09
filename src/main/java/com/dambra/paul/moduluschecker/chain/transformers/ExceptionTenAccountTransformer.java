package com.dambra.paul.moduluschecker.chain.transformers;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.chain.FirstModulusCheckRouter;
import com.dambra.paul.moduluschecker.chain.ModulusChainLink;
import com.dambra.paul.moduluschecker.chain.ModulusResult;

public final class ExceptionTenAccountTransformer implements ModulusChainLink {

    private final FirstModulusCheckRouter next;

    public ExceptionTenAccountTransformer(FirstModulusCheckRouter next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (params.firstWeightRow.get().isException(10)
                && shouldZeroiseUToB(params)) {
            BankAccount account = params.account.zeroiseUToB();
            params = params.withAccount(account);
        }
        return next.check(params);
    }

    // if ab = 09 or ab = 99 and g = 9, zeroise weighting positions u-b.
    private boolean shouldZeroiseUToB(ModulusCheckParams params) {
        int a = params.account.getNumberAt(BankAccount.A);
        int b = params.account.getNumberAt(BankAccount.B);
        int g = params.account.getNumberAt(BankAccount.G);

        return ((a == 0 || a == 9) && b == 9)
                && g == 9;
    }
}
