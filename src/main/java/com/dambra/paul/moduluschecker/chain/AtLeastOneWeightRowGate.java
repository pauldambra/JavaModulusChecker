package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;

import java.util.Optional;

public final class AtLeastOneWeightRowGate implements ModulusChainCheck {
    private final Optional<ModulusWeightRows> modulusWeightRows;
    private final FirstModulusCheckRouter next;

    public AtLeastOneWeightRowGate(Optional<ModulusWeightRows> modulusWeightRows, FirstModulusCheckRouter next) {
        this.modulusWeightRows = modulusWeightRows;
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (!modulusWeightRows.isPresent()) {
            return new ModulusResult(Optional.of(false), Optional.empty());
        }

        params = modulusWeightRows.get().FindFor(params.getAccount());

        if (!params.getFirstWeightRow().isPresent()) {
            return new ModulusResult(Optional.of(false), Optional.empty());
        }

        return next.check(params);
    }
}
