package com.github.pauldambra.moduluschecker.chain

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.SortCodeSubstitution
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFiveDoubleAlternateCheck
import com.github.pauldambra.moduluschecker.chain.checks.ModulusElevenCheck
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class SecondModulusCheckRouter(private val sortCodeSubstitution: SortCodeSubstitution) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      params.modulusResult!!.copy(
        secondCheckResult = runModulusCheck(params),
        secondException = params.secondWeightRow!!.exception
      )

    private fun runModulusCheck(params: ModulusCheckParams) =
      when (params.secondWeightRow!!.modulusAlgorithm) {
          ModulusAlgorithm.DOUBLE_ALTERNATE -> if (WeightRow.isExceptionFive(params.secondWeightRow))
              ExceptionFiveDoubleAlternateCheck(sortCodeSubstitution).check(params, params.secondWeightRow)
          else
              DoubleAlternateCheck().check(params.account, params.secondWeightRow)
          ModulusAlgorithm.MOD10            -> ModulusTenCheck().check(params.account, params.secondWeightRow)
          ModulusAlgorithm.MOD11            -> ModulusElevenCheck().check(params, params.secondWeightRow)
      }

}
