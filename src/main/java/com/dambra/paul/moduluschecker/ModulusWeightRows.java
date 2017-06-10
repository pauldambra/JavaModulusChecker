package com.dambra.paul.moduluschecker;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class ModulusWeightRows {
    private final ImmutableMap<String, WeightRow> weights;

    public ModulusWeightRows(ImmutableMap<SortCodeRange, WeightRow> weights) {
        this.weights = expand(weights);
    }

    private ImmutableMap<String, WeightRow> expand(ImmutableMap<SortCodeRange, WeightRow> weights) {
        Map<String, WeightRow> expandedRows = new HashMap<>();
        weights.forEach((scr, weightRow) -> scr.fullRange
                                               .forEach(sc -> expandedRows.put(sc, weightRow)));
        return ImmutableMap.copyOf(expandedRows);
    }

    public ModulusCheckParams FindFor(BankAccount account) {

        if (!weights.containsKey(account.sortCode)) {
            return new ModulusCheckParams(account, WeightRow.UNMATCHED_ACCOUNT);
        }

        return new ModulusCheckParams(account, weights.get(account.sortCode));
    }
}
