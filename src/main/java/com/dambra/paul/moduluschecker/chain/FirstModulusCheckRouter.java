package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;

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
    public Boolean check(ModulusCheckParams params) {

        boolean result = false;

        switch (params.firstWeightRow.get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = doubleAlternateCheck.check(params);
                break;
            case MOD10:
                result = modulusTenCheck.check(params);
                break;
            case MOD11:
                result = modulusElevenCheck.check(params);
                break;
        }

        return result;
    }
}
