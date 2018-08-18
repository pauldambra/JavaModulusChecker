package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFourteenModulusElevenCheck
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

/**
 * Exception Fourteen
 * Perform the modulus 11 check as normal:
 * • If the check passes (that is, there is no remainder), then the account number should be
 * considered valid. Do not perform the second check
 */
class ExceptionFourteenGate(private val next: ExceptionTwoAndNineGate) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (!WeightRow.isExceptionFourteen(params.firstWeightRow)) {
          next.check(params)
      } else {
          if (params.modulusResult!!.firstCheckResult!!) {
              ModulusResult.PASSES
          } else {
              params.modulusResult.copy(
                secondCheckResult = ExceptionFourteenModulusElevenCheck().check(params)
              )
          }
      }

}
