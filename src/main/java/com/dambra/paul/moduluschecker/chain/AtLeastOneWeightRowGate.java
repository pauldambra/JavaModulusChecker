package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;

public final class AtLeastOneWeightRowGate implements ModulusChainCheck {
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
