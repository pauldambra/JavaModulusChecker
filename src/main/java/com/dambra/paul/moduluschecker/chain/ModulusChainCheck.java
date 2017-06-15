package com.dambra.paul.moduluschecker.chain;

import com.dambra.paul.moduluschecker.ModulusCheckParams;

import java.util.Optional;

public interface ModulusChainCheck {
    ModulusResult check(ModulusCheckParams params);
}
