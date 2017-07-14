package com.github.pauldambra.moduluschecker.chain.gates;

import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink;
import com.github.pauldambra.moduluschecker.chain.ModulusResult;
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows;

import java.util.Optional;

public final class AtLeastOneWeightRowGate implements ModulusChainLink {
    private final ModulusWeightRows modulusWeightRows;
    private final ExceptionSixGate next;

    public AtLeastOneWeightRowGate(ModulusWeightRows modulusWeightRows, ExceptionSixGate next) {
        this.modulusWeightRows = modulusWeightRows;
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        params = modulusWeightRows.FindFor(params.account);

        return params.firstWeightRow.isPresent()
            ? next.check(params)
            : new ModulusResult(Optional.of(false), Optional.empty());
    }
}
