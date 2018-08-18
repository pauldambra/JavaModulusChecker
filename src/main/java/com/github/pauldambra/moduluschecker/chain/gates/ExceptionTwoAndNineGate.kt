package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.transformers.ExceptionSevenAccountTransformer
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionTwoAndNineGate(private val next: ExceptionSevenAccountTransformer) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (isExceptionTwoAndNine(params) && firstCheckSucceeded(params)) {
          params.modulusResult!!
      } else {
          next.check(params)
      }

    private fun isExceptionTwoAndNine(params: ModulusCheckParams) =
      WeightRow.isExceptionTwoAndNine(
          params.firstWeightRow,
          params.secondWeightRow)

    private fun firstCheckSucceeded(params: ModulusCheckParams) =
      params.modulusResult?.firstCheckResult ?: false
}
