package com.dambra.paul.moduluschecker.chain;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Optional;

public class ModulusResult {

    /** the default is for a check to pass **/
    static final ModulusResult PASSES = new ModulusResult(Optional.of(true), Optional.of(true));

    static final List<Integer> exceptionsThatRequireSecondCheck = ImmutableList.of(2, 5, 9, 10, 11, 12, 13, 14);

    public final Optional<Boolean> firstCheckResult;
    public final Optional<Boolean> secondCheckResult;
    public final Optional<Integer> firstException;
    public final Optional<Integer> secondException;

    public ModulusResult(Optional<Boolean> firstCheckResult, Optional<Boolean> secondCheckResult) {
        this.firstCheckResult = firstCheckResult;
        this.secondCheckResult = secondCheckResult;

        firstException = Optional.empty();
        secondException = Optional.empty();
    }

    private ModulusResult(
            Optional<Boolean> firstCheckResult,
            Optional<Boolean> secondCheckResult,
            Optional<Integer> firstException,
            Optional<Integer> secondException) {
        this.firstCheckResult = firstCheckResult;
        this.secondCheckResult = secondCheckResult;
        this.firstException = firstException;
        this.secondException = secondException;
    }

    public static ModulusResult withSecondResult(Optional<ModulusResult> modulusResult, Boolean secondCheck) {
        return new ModulusResult(
                modulusResult.map(mr -> mr.firstCheckResult).orElseGet(() -> Optional.of(false)),
                Optional.ofNullable(secondCheck),
                modulusResult.flatMap(mr -> mr.firstException),
                modulusResult.flatMap(mr -> mr.secondException));
    }

    public ModulusResult withFirstException(Optional<Integer> rowException) {
        return new ModulusResult(firstCheckResult, secondCheckResult, rowException, Optional.empty());
    }

    public ModulusResult withSecondException(Optional<Integer> rowException) {
        return new ModulusResult(firstCheckResult, secondCheckResult, firstException, rowException);
    }

    public Boolean processResults() {
        if (firstCheck()) {
            return secondCheckResult.isPresent()
                ? processTwoCheckResult()
                : firstCheck();
        }

        return exceptionsThatRequireSecondCheck.contains(firstException.orElse(-1))
            ? processTwoCheckResult()
            : false;
    }

    private Boolean processTwoCheckResult() {
        if (isExceptionFive()) {
            return bothMustPass();
        }

        if (isExceptionTen() || isExceptionTwelve()) {
            return firstOrSecond();
        }

        return secondCheck();
    }

    private boolean isExceptionFive() {
        return firstException.isPresent() && firstException.get() == 5;
    }

    private boolean isExceptionTen() {
        return firstException.isPresent() && firstException.get() == 10;
    }

    private boolean isExceptionTwelve() {
        return firstException.isPresent() && firstException.get() == 12;
    }

    private boolean firstCheck() {
        return firstCheckResult.orElse(false);
    }

    private boolean secondCheck() {
        return secondCheckResult.orElse(false);
    }

    private boolean firstOrSecond() {
        return firstCheck() || secondCheck();
    }

    private boolean bothMustPass() {
        return firstCheck() && secondCheck();
    }

    public static ModulusResult copy(ModulusResult original) {
        if (original == null) { return null; }
        return new ModulusResult(
          original.firstCheckResult, original.secondCheckResult, original.firstException, original.secondException
        );
    }

    @Override
    public String toString() {
        return "ModulusResult{" +
                "firstCheckPassed=" + firstCheckResult +
                ", secondCheckResult=" + secondCheckResult +
                ", firstException=" + firstException +
                ", secondException=" + secondException +
                '}';
    }
}
