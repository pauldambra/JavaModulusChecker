package com.dambra.paul.moduluschecker.chain;

import java.util.Optional;

public class ModulusResult {
    public final Optional<Boolean> firstCheck;
    public final Optional<Boolean> secondCheck;

    public ModulusResult(Optional<Boolean> firstCheck, Optional<Boolean> secondCheck) {
        this.firstCheck = firstCheck;
        this.secondCheck = secondCheck;
    }
}
