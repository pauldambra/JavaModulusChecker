package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public final class ExceptionSevenAccountTransformer implements ModulusChainLink {

    private final ModulusChainLink next;
    private final Function<ModulusCheckParams, WeightRow> rowSelector;

    public ExceptionSevenAccountTransformer(
            ModulusChainLink next,
            Function<ModulusCheckParams, WeightRow> rowSelector) {
        this.next = next;
        this.rowSelector = rowSelector;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (rowSelector.apply(params).isException(7)) {
            BankAccount account = params.account.zeroiseUToB();
            params = params.withAccount(account);
        }
        return next.check(params);
    }
}
