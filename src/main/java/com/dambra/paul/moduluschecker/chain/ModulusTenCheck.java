package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.WeightRow;
import com.dambra.paul.moduluschecker.checks.ModulusEleven;

public class ModulusTenCheck implements ModulusChainCheck {
    @Override
    public Boolean check(ModulusCheckParams params) {
        WeightRow firstWeightRow = params.weightRows.get().get(0);

        return ModulusEleven.check(params.account.allDigits(), firstWeightRow.weights);
    }
}
