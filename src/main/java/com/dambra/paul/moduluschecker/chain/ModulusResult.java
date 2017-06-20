package com.dambra.paul.moduluschecker.chain;

import java.util.Optional;

public class ModulusResult {
    public final Optional<Boolean> firstCheck;
    public final Optional<Boolean> secondCheck;
    public final boolean isExceptionFive;
    public final boolean isExceptionTen;
    public final boolean isExceptionTwelve;

    public ModulusResult(Optional<Boolean> firstCheck, Optional<Boolean> secondCheck) {
        this.firstCheck = firstCheck;
        this.secondCheck = secondCheck;
        this.isExceptionFive = false;
        this.isExceptionTen = false;
        this.isExceptionTwelve = false;
    }

    private ModulusResult(
            Optional<Boolean> firstCheck,
            Optional<Boolean> secondCheck,
            boolean isExceptionFive,
            boolean isExceptionTen,
            boolean isExceptionTwelve) {
        this.firstCheck = firstCheck;
        this.secondCheck = secondCheck;
        this.isExceptionFive = isExceptionFive;
        this.isExceptionTen = isExceptionTen;
        this.isExceptionTwelve = isExceptionTwelve;
    }

    public static ModulusResult WithSecondResult(Optional<ModulusResult> modulusResult, Boolean secondCheck) {
        Optional<Boolean> firstCheck = modulusResult.map(mr -> mr.firstCheck)
                                                    .orElseGet(() -> Optional.of(false));

        boolean isExceptionFive = modulusResult.isPresent() && modulusResult.get().isExceptionFive;
        boolean isExceptionTen = modulusResult.isPresent() && modulusResult.get().isExceptionTen;
        boolean isExceptionTwelve = modulusResult.isPresent() && modulusResult.get().isExceptionTwelve;

        return new ModulusResult(firstCheck, Optional.ofNullable(secondCheck), isExceptionFive, isExceptionTen, isExceptionTwelve);
    }

    public static ModulusResult WasProcessedAsExceptionFive(ModulusResult original) {
        return new ModulusResult(original.firstCheck, original.secondCheck, true, false, false);
    }

    public static ModulusResult WasProcessedAsExceptionTen(ModulusResult original) {
        return new ModulusResult(original.firstCheck, original.secondCheck, false, true, false);
    }

    public static ModulusResult WasProcessedAsExceptionTwelve(ModulusResult original) {
        return new ModulusResult(original.firstCheck, original.secondCheck, false, false, true);
    }

    public static ModulusResult copy(ModulusResult original) {
        if (original == null) { return null; }
        return new ModulusResult(
          original.firstCheck, original.secondCheck, original.isExceptionFive, original.isExceptionTen, original.isExceptionTwelve
        );
    }

    public Boolean processResults() {
        if (isExceptionFive) {
            return firstCheck.orElse(false)
                    && secondCheck.orElse(false);
        }

        if (isExceptionTen || isExceptionTwelve) {
            return firstCheck.orElse(false)
                    || secondCheck.orElse(false);
        }

        if (secondCheck.isPresent()) {
            return secondCheck.get();
        }

        return firstCheck.orElse(false);
    }

    @Override
    public String toString() {
        return "ModulusResult{" +
                "firstCheck=" + firstCheck +
                ", secondCheck=" + secondCheck +
                ", isExceptionFive=" + isExceptionFive +
                ", isExceptionTen=" + isExceptionTen +
                ", isExceptionTwelve=" + isExceptionTwelve +
                '}';
    }
}
