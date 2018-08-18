package com.github.pauldambra.moduluschecker.chain

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.SortCodeSubstitution
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck
import com.github.pauldambra.moduluschecker.chain.checks.ExceptionFiveDoubleAlternateCheck
import com.github.pauldambra.moduluschecker.chain.checks.ModulusElevenCheck
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import com.google.common.collect.ImmutableMap

class SecondModulusCheckRouter(sortCodeSubstitution: SortCodeSubstitution) : ModulusChainLink {

    init {

        checkAlgorithm = ImmutableMap.builder<ModulusAlgorithm, (ModulusCheckParams) -> Boolean>()
          .put(
            ModulusAlgorithm.DOUBLE_ALTERNATE,
            { params ->
                if (WeightRow.isExceptionFive(params.secondWeightRow))
                    ExceptionFiveDoubleAlternateCheck(sortCodeSubstitution).check(params, rowSelector)
                else
                    DoubleAlternateCheck().check(rowSelector(params), params.account)
            }
          )
          .put(
            ModulusAlgorithm.MOD10,
            { params -> ModulusTenCheck().check(rowSelector(params), params.account) }
          )
          .put(
            ModulusAlgorithm.MOD11,
            { params -> ModulusElevenCheck().check(params, rowSelector(params)) }
          )
          .build()
    }

    override fun check(params: ModulusCheckParams): ModulusResult {
        val result = runModulusCheck(params)
        return updateResult(params, result)
    }

    private fun runModulusCheck(params: ModulusCheckParams): Boolean {
        val modulusAlgorithm = params.secondWeightRow?.modulusAlgorithm
        return if (checkAlgorithm.containsKey(modulusAlgorithm)) {
            checkAlgorithm[modulusAlgorithm]?.invoke(params)!!
        } else false
    }

    private fun updateResult(params: ModulusCheckParams, result: Boolean): ModulusResult {
        return ModulusResult
          .withSecondResult(params.modulusResult, result)
          .withSecondException(params.secondWeightRow?.exception)
    }

    companion object {

        private val rowSelector = { p: ModulusCheckParams -> p.secondWeightRow!! }
        private lateinit var checkAlgorithm: Map<ModulusAlgorithm, (mcp: ModulusCheckParams) -> Boolean>
    }

}
