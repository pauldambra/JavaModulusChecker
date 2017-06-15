package com.dambra.paul.moduluschecker;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class WeightRow {
    private static final Splitter splitter = Splitter.on(CharMatcher.whitespace())
                                              .omitEmptyStrings();

    public final ModulusAlgorithm modulusAlgorithm;
    public final Optional<Integer> exception;
    public final Stream<Integer> weights;

    private static final int MODULUS_INDEX = 2;
    private static final int EXCEPTION_INDEX = 17;

    public WeightRow(ModulusAlgorithm modulusAlgorithm, Stream<Integer> weights, Optional<Integer> exception) {
        this.modulusAlgorithm = modulusAlgorithm;
        this.exception = exception;
        this.weights = weights;
    }

    public boolean isExceptionOne() {
        return exception.isPresent() && exception.get() == 1;
    }

    public static Optional<WeightRow> parse(String input) {
        List<String> parts = splitter.splitToList(input);

        Stream<Integer> weights = parts.stream()
                                .skip(MODULUS_INDEX + 1)
                                .limit(EXCEPTION_INDEX - MODULUS_INDEX - 1)
                                .mapToInt(Integer::parseInt)
                                .boxed();

        Integer exception = null;
        if (parts.size() == EXCEPTION_INDEX + 1) {
            exception = Integer.parseInt(parts.get(EXCEPTION_INDEX));
        }

        ModulusAlgorithm modulusAlgorithm;
        try {
            modulusAlgorithm = ModulusAlgorithm.parse(parts.get(MODULUS_INDEX));
        } catch (UnknownAlgorithmException e) {
            //TODO how do I log?
            return Optional.empty();
        }

        return Optional.of(new WeightRow(
                modulusAlgorithm,
                weights,
                Optional.ofNullable(exception)
        ));
    }
}
