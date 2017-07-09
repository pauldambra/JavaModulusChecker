package com.dambra.paul.moduluschecker.chain.gates;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.chain.ModulusChainLink;
import com.dambra.paul.moduluschecker.chain.ModulusResult;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;

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
