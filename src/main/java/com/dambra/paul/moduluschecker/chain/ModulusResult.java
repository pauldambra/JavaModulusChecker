package com.dambra.paul.moduluschecker.chain;

import java.util.Optional;

public class ModulusResult {
    public final Optional<Boolean> firstCheck;
    public final Optional<Boolean> secondCheck;

    public ModulusResult(Optional<Boolean> firstCheck, Optional<Boolean> secondCheck) {
        this.firstCheck = firstCheck;
        this.secondCheck = secondCheck;
    }

    public static ModulusResult WithSecondResult(Optional<ModulusResult> modulusResult, Boolean secondCheck) {
        Optional<Boolean> firstCheck = modulusResult.isPresent()
                                        ? modulusResult.get().firstCheck
                                        : Optional.of(false);
        return new ModulusResult(firstCheck, Optional.of(secondCheck));
    }

    public static ModulusResult copy(ModulusResult original) {
        if (original == null) { return null; }
        return new ModulusResult(
          original.firstCheck, original.secondCheck
        );
    }
}
