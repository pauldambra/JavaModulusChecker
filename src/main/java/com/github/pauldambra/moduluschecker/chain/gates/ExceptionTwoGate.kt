package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionTwoGate(private val next: ExceptionFourteenGate) : ModulusChainLink {

    override fun check(params: ModulusCheckParams): ModulusResult {
        return if (WeightRow.isExceptionTwo(params.firstWeightRow) && ModulusResult.firstCheckPassed(params.modulusResult)) {
            params.modulusResult!!
        } else next.check(params)

    }
}
