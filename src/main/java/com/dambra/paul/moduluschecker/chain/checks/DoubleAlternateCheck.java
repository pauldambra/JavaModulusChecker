package com.dambra.paul.moduluschecker.chain.checks;

import com.dambra.paul.moduluschecker.As;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.Streams;

import java.util.function.Function;

public final class DoubleAlternateCheck {

    public Boolean check(ModulusCheckParams params, Function<ModulusCheckParams, WeightRow> rowSelector) {
        WeightRow selectedRow = rowSelector.apply(params);

        int total = Streams.zip(
                params.getAccount().allDigits(),
                selectedRow.getWeights().stream(),
                        (l, r) -> l * r
                    ).map(String::valueOf)
                    .flatMap(As::integerStream)
                    .reduce(0, Integer::sum);

        if (selectedRow.isExceptionOne()) {
            total += 27;
        }

        return total % 10 == 0;
    }
}
