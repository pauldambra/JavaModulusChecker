package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionTwoGate(private val next: ExceptionFourteenGate) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (WeightRow.isExceptionTwo(params.firstWeightRow) && params.modulusResult!!.firstCheckResult!!) {
          params.modulusResult
      } else {
          next.check(params)
      }
}
