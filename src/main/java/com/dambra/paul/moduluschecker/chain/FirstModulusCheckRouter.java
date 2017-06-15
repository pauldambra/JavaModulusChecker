package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;

import java.util.Optional;

public class FirstModulusCheckRouter implements ModulusChainCheck {
    private final DoubleAlternateCheck doubleAlternateCheck;
    private final ModulusTenCheck modulusTenCheck;
    private final ModulusElevenCheck modulusElevenCheck;

    public FirstModulusCheckRouter(
            DoubleAlternateCheck doubleAlternateCheck,
            ModulusTenCheck modulusTenCheck,
            ModulusElevenCheck modulusElevenCheck) {
        this.doubleAlternateCheck = doubleAlternateCheck;
        this.modulusTenCheck = modulusTenCheck;
        this.modulusElevenCheck = modulusElevenCheck;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        boolean result = false;

        switch (params.firstWeightRow.get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = doubleAlternateCheck.check(params, p -> p.firstWeightRow.get());
                break;
            case MOD10:
                result = modulusTenCheck.check(params, p -> p.firstWeightRow.get());
                break;
            case MOD11:
                result = modulusElevenCheck.check(params, p -> p.firstWeightRow.get());
                break;
        }

        return new ModulusResult(Optional.of(result), Optional.empty());
    }
}
