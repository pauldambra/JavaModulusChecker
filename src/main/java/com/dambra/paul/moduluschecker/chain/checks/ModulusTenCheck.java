package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public class ModulusTenCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        int total = ModulusTotal.calculate(params.account, selectedRow.getWeights());

        return total % 10 == 0;
    }

}
