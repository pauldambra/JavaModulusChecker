package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.checks.DoubleAlternate;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.WeightRow;

public class DoubleAlternateCheck implements ModulusChainCheck {

    @Override
    public Boolean check(ModulusCheckParams params) {
        WeightRow firstWeightRow = params.weightRows.get().get(0);

        return DoubleAlternate.check(params.account.allDigits(), firstWeightRow.weights);
    }
}
