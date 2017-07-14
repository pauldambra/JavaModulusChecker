package com.github.pauldambra.moduluschecker.chain.checks;

import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

import java.util.function.Function;

public class ModulusTenCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        int total = ModulusTotal.calculate(params.account, selectedRow.getWeights());

        return total % 10 == 0;
    }

}
