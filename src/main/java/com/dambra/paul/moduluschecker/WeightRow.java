package com.dambra.paul.moduluschecker;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.List;
import java.util.Optional;

public class WeightRow {

    public final ModulusAlgorithm modulusAlgorithm;
    public final Optional<Integer> exception;
    public final int[] weights;

    private static final int MODULUS_INDEX = 2;
    private static final int EXCEPTION_INDEX = 17;

    public WeightRow(ModulusAlgorithm modulusAlgorithm, int[] weights, Optional<Integer> exception) {
        this.modulusAlgorithm = modulusAlgorithm;
        this.exception = exception;
        this.weights = weights;
    }

    public static WeightRow parse(String input) throws UnknownAlgorithmException {
        Splitter splitter = Splitter.on(CharMatcher.whitespace())
                .omitEmptyStrings();

        List<String> parts = splitter.splitToList(input);

        int[] weights = parts.stream()
                                .skip(MODULUS_INDEX + 1)
                                .limit(EXCEPTION_INDEX - MODULUS_INDEX - 1)
                                .mapToInt(Integer::parseInt)
                                .toArray();

        Integer exception = null;
        if (parts.size() == EXCEPTION_INDEX + 1) {
            exception = Integer.parseInt(parts.get(EXCEPTION_INDEX));
        }

        return new WeightRow(
                ModulusAlgorithm.parse(parts.get(MODULUS_INDEX)),
                weights,
                Optional.ofNullable(exception)
        );
    }
}
