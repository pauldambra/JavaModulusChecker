package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.ModulusWeightRows;

import java.util.Optional;

public class AtLeastOneWeightRowGate implements ModulusChainCheck {
    private final Optional<ModulusWeightRows> modulusWeightRows;
    private final FirstModulusCheckRouter next;

    public AtLeastOneWeightRowGate(Optional<ModulusWeightRows> modulusWeightRows, FirstModulusCheckRouter next) {
        this.modulusWeightRows = modulusWeightRows;
        this.next = next;
    }

    @Override
    public Boolean check(ModulusCheckParams params) {
        if (!modulusWeightRows.isPresent()) {
            return false;
        }

        params = modulusWeightRows.get().FindFor(params.account);

        if (!params.firstWeightRow.isPresent()) {
            return false;
        }

        return next.check(params);
    }
}
