package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public final class DoubleAlternateCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        int total = ModulusTotal.calculateDoubleAlternate(params.account, selectedRow.getWeights());

        if (selectedRow.isException(1)) {
            total += 27;
        }

        final int remainder = total % 10;
        return remainder == 0;
    }
}
