package com.dambra.paul.moduluschecker.checks;

import com.dambra.paul.moduluschecker.As;
import com.google.common.collect.Streams;

import java.util.stream.Stream;

public class DoubleAlternate {
    public static Boolean check(Stream<Integer> accountDigits, Stream<Integer> weights) {

        int total = Streams.zip(
                        accountDigits,
                        weights,
                        (l, r) -> l * r
                    ).map(String::valueOf)
                    .flatMap(As::integerStream)
                    .reduce(0, Integer::sum);

        return total % 10 == 0;
    }
}
