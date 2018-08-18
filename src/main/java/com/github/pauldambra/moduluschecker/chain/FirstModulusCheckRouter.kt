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
import com.google.common.collect.ImmutableMap

class FirstModulusCheckRouter(
  sortCodeSubstitution: SortCodeSubstitution,
  private val next: SecondCheckRequiredGate) : ModulusChainLink {

    init {

        checkAlgorithm = ImmutableMap.builder<ModulusAlgorithm, (mcp: ModulusCheckParams) -> Boolean>()
          .put(
            ModulusAlgorithm.DOUBLE_ALTERNATE,
            { params -> DoubleAlternateCheck().check(params, rowSelector) }
          )
          .put(
            ModulusAlgorithm.MOD10,
            { params -> ModulusTenCheck().check(params, rowSelector) }
          )
          .put(
            ModulusAlgorithm.MOD11,
            { params ->
                if (WeightRow.isExceptionFive(params.firstWeightRow))
                    ExceptionFiveModulusElevenCheck(sortCodeSubstitution).check(params, rowSelector)
                else
                    ModulusElevenCheck().check(params, rowSelector)
            })
          .build()
    }

    override fun check(params: ModulusCheckParams): ModulusResult {
        val result = runModulusCheck(params)
        val updatedParams = updateParamsWithResult(params, result)
        return next.check(updatedParams)
    }

    private fun runModulusCheck(params: ModulusCheckParams): Boolean {
        val modulusAlgorithm = params.firstWeightRow?.modulusAlgorithm
        return if (checkAlgorithm.containsKey(modulusAlgorithm)) {
            checkAlgorithm[modulusAlgorithm]?.invoke(params)!!
        } else false
    }

    private fun updateParamsWithResult(params: ModulusCheckParams, result: Boolean): ModulusCheckParams {
        val modulusResult = ModulusResult
          .WithFirstResult(result)
          .withFirstException(params.firstWeightRow?.exception)

        return ModulusCheckParams(params.account, params.firstWeightRow, params.secondWeightRow, modulusResult)
    }

    companion object {

        private val rowSelector = { p: ModulusCheckParams -> p.firstWeightRow!! }
        private lateinit var checkAlgorithm: Map<ModulusAlgorithm, (mcp: ModulusCheckParams) -> Boolean>
    }

}
