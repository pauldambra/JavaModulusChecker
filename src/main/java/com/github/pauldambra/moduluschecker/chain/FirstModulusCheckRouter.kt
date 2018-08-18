package com.github.pauldambra.moduluschecker.chain

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.SortCodeSubstitution
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFiveModulusElevenCheck
import com.github.pauldambra.moduluschecker.chain.checks.ModulusElevenCheck
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck
import com.github.pauldambra.moduluschecker.chain.gates.SecondCheckRequiredGate
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class FirstModulusCheckRouter(
  private val sortCodeSubstitution: SortCodeSubstitution,
  private val next: SecondCheckRequiredGate) : ModulusChainLink {

    override fun check(params: ModulusCheckParams): ModulusResult {
        val modulusResult = ModulusResult
          .WithFirstResult(runModulusCheck(params))
          .withFirstException(params.firstWeightRow?.exception)

        return next.check(params.copy(modulusResult = modulusResult))
    }

    private fun runModulusCheck(params: ModulusCheckParams) =
      when (params.firstWeightRow!!.modulusAlgorithm) {
        ModulusAlgorithm.DOUBLE_ALTERNATE -> DoubleAlternateCheck().check(params.account, params.firstWeightRow)
        ModulusAlgorithm.MOD10 -> ModulusTenCheck().check(params.account, params.firstWeightRow)
        ModulusAlgorithm.MOD11 -> if (WeightRow.isExceptionFive(params.firstWeightRow))
                                        ExceptionFiveModulusElevenCheck(sortCodeSubstitution).check(params, params.firstWeightRow)
                                    else
                                        ModulusElevenCheck().check(params, params.firstWeightRow)
    }

}
