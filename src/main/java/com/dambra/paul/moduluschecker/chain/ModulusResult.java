package com.dambra.paul.moduluschecker.chain;

import java.util.Optional;

public class ModulusResult {
    public final Optional<Boolean> firstCheck;
    public final Optional<Boolean> secondCheck;
    public final boolean isExceptionFive;

    public ModulusResult(Optional<Boolean> firstCheck, Optional<Boolean> secondCheck) {
        this.firstCheck = firstCheck;
        this.secondCheck = secondCheck;
        this.isExceptionFive = false;
    }

    private ModulusResult(Optional<Boolean> firstCheck, Optional<Boolean> secondCheck, boolean isExceptionFive) {
        this.firstCheck = firstCheck;
        this.secondCheck = secondCheck;
        this.isExceptionFive = isExceptionFive;
    }

    public static ModulusResult WithSecondResult(Optional<ModulusResult> modulusResult, Boolean secondCheck) {
        Optional<Boolean> firstCheck = modulusResult.map(mr -> mr.firstCheck)
                                                    .orElseGet(() -> Optional.of(false));
        boolean isExceptionFive = modulusResult.isPresent() && modulusResult.get().isExceptionFive;
        return new ModulusResult(firstCheck, Optional.ofNullable(secondCheck), isExceptionFive);
    }

    public static ModulusResult WasProcessedAsExceptionFive(ModulusResult original) {
        return new ModulusResult(original.firstCheck, original.secondCheck, true);
    }

    public static ModulusResult copy(ModulusResult original) {
        if (original == null) { return null; }
        return new ModulusResult(
          original.firstCheck, original.secondCheck, original.isExceptionFive
        );
    }

    @Override
    public String toString() {
        return "ModulusResult{" +
                "firstCheck=" + firstCheck +
                ", secondCheck=" + secondCheck +
                '}';
    }
}
