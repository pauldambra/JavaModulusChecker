package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows

class AtLeastOneWeightRowGate(private val modulusWeightRows: ModulusWeightRows, private val next: ExceptionSixGate) : ModulusChainLink {

    override fun check(params: ModulusCheckParams): ModulusResult {
        val paramsWithFoundWeights = modulusWeightRows.findFor(params.account)

        return if (paramsWithFoundWeights.firstWeightRow != null) {
            next.check(paramsWithFoundWeights)
        } else {
            ModulusResult(false, null)
        }
    }
}
