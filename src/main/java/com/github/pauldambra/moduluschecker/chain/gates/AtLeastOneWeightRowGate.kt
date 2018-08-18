package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows

class AtLeastOneWeightRowGate(private val modulusWeightRows: ModulusWeightRows, private val next: ExceptionSixGate) : ModulusChainLink {

    override fun check(params: ModulusCheckParams): ModulusResult {
        var paramsToPassAlong = params
        paramsToPassAlong = modulusWeightRows.FindFor(paramsToPassAlong.account)

        return if (paramsToPassAlong.firstWeightRow != null) {
            next.check(paramsToPassAlong)
        } else {
            ModulusResult(false, null)
        }
    }
}
