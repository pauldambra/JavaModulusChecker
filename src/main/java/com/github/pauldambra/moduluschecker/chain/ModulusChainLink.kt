package com.github.pauldambra.moduluschecker.chain

import com.github.pauldambra.moduluschecker.ModulusCheckParams

interface ModulusChainLink {
    fun check(params: ModulusCheckParams): ModulusResult
}
