package com.github.pauldambra.moduluschecker.chain;

import com.github.pauldambra.moduluschecker.ModulusCheckParams;

public interface ModulusChainLink {
    ModulusResult check(ModulusCheckParams params);
}
