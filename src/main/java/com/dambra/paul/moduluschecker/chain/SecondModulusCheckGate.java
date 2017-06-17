package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;

public class SecondModulusCheckGate implements ModulusChainCheck {

    private final SecondModulusCheckRouter next;

    public SecondModulusCheckGate(SecondModulusCheckRouter next) {
        this.next = next;
    }

    @Override
    public ModulusResult check(ModulusCheckParams params) {
        if (!params.getSecondWeightRow().isPresent()) { return params.getModulusResult().get(); }

        return next.check(params);
    }
}
