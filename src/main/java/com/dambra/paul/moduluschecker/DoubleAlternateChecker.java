package com.dambra.paul.moduluschecker;

import com.google.common.collect.Streams;

public class DoubleAlternateChecker {
    public Boolean check(ModulusCheckParams params) {

        int total = Streams.zip(
                        params.account.allDigits(),
                        params.weightRows.get().get(0).weights,
                        (l, r) -> l * r
                    ).map(String::valueOf)
                    .flatMap(As::integerStream)
                    .reduce(0, Integer::sum);

        return total % 10 == 0;
    }
}
