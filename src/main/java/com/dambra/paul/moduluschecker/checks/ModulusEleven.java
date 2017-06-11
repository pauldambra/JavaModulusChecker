package com.dambra.paul.moduluschecker.checks;

import com.google.common.collect.Streams;

import java.util.stream.Stream;

public class ModulusEleven {

    public static Boolean check(Stream<Integer> accountDigits, Stream<Integer> weights) {

        int total = Streams.zip(
                accountDigits,
                weights,
                (l, r) -> l * r
        ).reduce(0, Integer::sum);

        return total % 11 == 0;
    }
}
