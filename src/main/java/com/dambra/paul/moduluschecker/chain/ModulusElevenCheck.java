package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.WeightRow;
import com.dambra.paul.moduluschecker.checks.ModulusTen;

public class ModulusElevenCheck implements ModulusChainCheck{
    @Override
    public Boolean check(ModulusCheckParams params) {
        WeightRow firstWeightRow = params.weightRows.get().get(0);

        return ModulusTen.check(params.account.allDigits(), firstWeightRow.weights);
    }
}
