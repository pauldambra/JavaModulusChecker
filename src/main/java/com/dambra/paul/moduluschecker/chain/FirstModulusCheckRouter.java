package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;
import java.util.function.Function;


public final class FirstModulusCheckRouter implements ModulusChainCheck {
    private final DoubleAlternateCheck doubleAlternateCheck;
    private final ModulusTenCheck modulusTenCheck;
    private final ModulusElevenCheck modulusElevenCheck;
    private final SecondModulusCheckGate next;

    public FirstModulusCheckRouter(
            DoubleAlternateCheck doubleAlternateCheck,
            ModulusTenCheck modulusTenCheck,
            ModulusElevenCheck modulusElevenCheck,
            SecondModulusCheckGate secondModulusCheckGate) {
        this.doubleAlternateCheck = doubleAlternateCheck;
        this.modulusTenCheck = modulusTenCheck;
        this.modulusElevenCheck = modulusElevenCheck;
        this.next = secondModulusCheckGate;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {

        boolean result = false;

        Function<ModulusCheckParams, WeightRow> rowSelector = p -> p.getFirstWeightRow().get();
        switch (params.getFirstWeightRow().get().modulusAlgorithm) {
            case DOUBLE_ALTERNATE:
                result = doubleAlternateCheck.check(params, rowSelector);
                break;
            case MOD10:
                result = modulusTenCheck.check(params, rowSelector);
                break;
            case MOD11:
                result = modulusElevenCheck.check(params, rowSelector);
                break;
        }

        return next.check(new ModulusCheckParams(
                params.getAccount(),
                params.getFirstWeightRow(),
                params.getSecondWeightRow(),
                Optional.of(new ModulusResult(Optional.of(result), Optional.empty()))
        ));
    }
}
