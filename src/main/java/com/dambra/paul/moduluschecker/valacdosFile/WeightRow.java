package com.dambra.paul.moduluschecker.valacdosFile;

import com.dambra.paul.moduluschecker.ModulusAlgorithm;
import com.dambra.paul.moduluschecker.UnknownAlgorithmException;
import com.google.common.base.CharMatcher;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class WeightRow {
    private static final Splitter splitter = Splitter.on(CharMatcher.whitespace())
                                              .omitEmptyStrings();

    public final ModulusAlgorithm modulusAlgorithm;
    public final Optional<Integer> exception;
    private final ImmutableList<Integer> weights;

    private static final int MODULUS_INDEX = 2;
    private static final int EXCEPTION_INDEX = 17;

    public WeightRow(ModulusAlgorithm modulusAlgorithm, List<Integer> weights, Optional<Integer> exception) {
        this.modulusAlgorithm = modulusAlgorithm;
        this.exception = exception;
        this.weights = ImmutableList.copyOf(weights);
    }

    public static boolean isExceptionTwoAndNine(Optional<WeightRow> firstRow, Optional<WeightRow> secondWeightRow) {
        return firstRow.isPresent()
                && firstRow.get().isExceptionTwo()
                && secondCheckIsExceptionNine(secondWeightRow);
    }

    private static boolean secondCheckIsExceptionNine(Optional<WeightRow> secondWeightRow) {
        return secondWeightRow.isPresent()
                && secondWeightRow.get().isExceptionNine();
    }

    public boolean isExceptionOne() {
        return isException(1);
    }

    public boolean isExceptionTwo() {
        return isException(2);
    }

    public boolean isExceptionFour() {
        return isException(4);
    }

    public boolean isExceptionFive() {
        return isException(5);
    }

    public boolean isExceptionSix() {
        return isException(6);
    }

    public boolean isExceptionSeven() {
        return isException(7);
    }

    public boolean isExceptionEight() {
        return isException(8);
    }

    public boolean isExceptionNine() {
        return isException(9);
    }

    public boolean isExceptionTen() {
        return isException(10);
    }

    public boolean isExceptionTwelve() {
        return isException(12);
    }

    private boolean isException(int exceptionNumber) {
        return exception.isPresent() && exception.get() == exceptionNumber;
    }

    public static Optional<WeightRow> parse(String input) {
        List<String> parts = splitter.splitToList(input);

        List<Integer> weights = parts.stream()
                                .skip(MODULUS_INDEX + 1)
                                .limit(EXCEPTION_INDEX - MODULUS_INDEX - 1)
                                .mapToInt(Integer::parseInt)
                                .boxed()
                                .collect(Collectors.toList());

        Integer exception = null;
        if (parts.size() == EXCEPTION_INDEX + 1) {
            exception = Integer.parseInt(parts.get(EXCEPTION_INDEX));
        }

        ModulusAlgorithm modulusAlgorithm;
        try {
            modulusAlgorithm = ModulusAlgorithm.parse(parts.get(MODULUS_INDEX));
        } catch (UnknownAlgorithmException e) {
            return Optional.empty();
        }

        return Optional.of(new WeightRow(
                modulusAlgorithm,
                weights,
                Optional.ofNullable(exception)
        ));
    }

    public ImmutableList<Integer> getWeights() {
        return ImmutableList.copyOf(weights);
    }

    public static WeightRow copy(WeightRow original) {
        if (original == null) { return null; }
        return new WeightRow(
                original.modulusAlgorithm,
                original.getWeights(),
                original.exception);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightRow weightRow = (WeightRow) o;
        return modulusAlgorithm == weightRow.modulusAlgorithm &&
                Objects.equal(exception, weightRow.exception) &&
                Objects.equal(getWeights(), weightRow.getWeights());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(modulusAlgorithm, exception, getWeights());
    }

    @Override
    public String toString() {
        return "WeightRow{" +
                "modulusAlgorithm=" + modulusAlgorithm +
                " weights=" + weights +
                " exception=" + exception +
                '}';
    }
}
