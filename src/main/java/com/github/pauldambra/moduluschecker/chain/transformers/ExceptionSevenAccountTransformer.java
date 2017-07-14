package com.github.pauldambra.moduluschecker.chain.transformers;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink;
import com.github.pauldambra.moduluschecker.chain.ModulusResult;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

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
