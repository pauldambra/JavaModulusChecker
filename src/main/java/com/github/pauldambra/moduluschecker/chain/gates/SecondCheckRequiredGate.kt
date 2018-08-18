package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class SecondCheckRequiredGate(private val next: ExceptionTwoGate) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (exceptionRequiresSecondCheck(params)) {
          next.check(params)
      } else {
          if (params.secondWeightRow != null) {
              next.check(params)
          } else {
              params.modulusResult ?: ModulusResult.PASSES
          }
      }

    private fun exceptionRequiresSecondCheck(params: ModulusCheckParams) =
      rowExceptionRequiresSecondCheck(params.firstWeightRow) || rowExceptionRequiresSecondCheck(params.secondWeightRow)

    private fun rowExceptionRequiresSecondCheck(row: WeightRow?) =
      ModulusResult.exceptionsThatRequireSecondCheck.contains(row?.exception ?: -1)
}
