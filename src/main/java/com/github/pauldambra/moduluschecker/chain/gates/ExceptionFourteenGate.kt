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

    override fun check(params: ModulusCheckParams): ModulusResult {

        if (!WeightRow.isExceptionFourteen(params.firstWeightRow)) {
            return next.check(params)
        }

        return if (ModulusResult.firstCheckPassed(params.modulusResult))
            ModulusResult(true, null)
        else
            ModulusResult.withSecondResult(
              params.modulusResult,
              ExceptionFourteenModulusElevenCheck().check(params))
    }

}
