package com.github.pauldambra.moduluschecker

import com.github.pauldambra.moduluschecker.chain.FirstModulusCheckRouter
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.SecondModulusCheckRouter
import com.github.pauldambra.moduluschecker.chain.gates.*
import com.github.pauldambra.moduluschecker.chain.transformers.ExceptionEightAccountTransformer
import com.github.pauldambra.moduluschecker.chain.transformers.ExceptionSevenAccountTransformer
import com.github.pauldambra.moduluschecker.chain.transformers.ExceptionTenAccountTransformer
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows

internal object ModulusCheckingChain {
    /**
     * AtLeastOneWeightRowGate
     * |
     * V
     * ExceptionSixGate
     * |
     * V
     * ExceptionSevenAccountTransformer
     * |
     * V
     * ExceptionEightAccountTransformer
     * |
     * V
     * ExceptionTenAccountTransformer
     * |
     * V
     * FirstModulusCheckRouter
     * |
     * V
     * SecondCheckRequiredGate
     * |
     * V
     * ExceptionTwoGate
     * |
     * V
     * ExceptionFourteenGate
     * |
     * V
     * ExceptionTwoAndNineGate
     * |
     * V
     * ExceptionSevenAccountTransformer
     * |
     * V
     * ExceptionEightAccountTransformer
     * |
     * V
     * ExceptionThreeGate
     * |
     * V
     * SecondModulusCheckRouter
     */
    fun create(weightRows: ModulusWeightRows, sortCodeSubstitution: SortCodeSubstitution): ModulusChainLink {
        val secondModulusCheckRouter = SecondModulusCheckRouter(sortCodeSubstitution)
        val exceptionThreeGate = ExceptionThreeGate(secondModulusCheckRouter)
        val secondExceptionEightAccountTransformer = ExceptionEightAccountTransformer(exceptionThreeGate) { p -> p.secondWeightRow!! }
        val secondExceptionSevenAccountTransformer = ExceptionSevenAccountTransformer(secondExceptionEightAccountTransformer) { p -> p.secondWeightRow!! }
        val exceptionTwoAndNineGate = ExceptionTwoAndNineGate(secondExceptionSevenAccountTransformer)
        val exceptionFourteenGate = ExceptionFourteenGate(exceptionTwoAndNineGate)
        val exceptionTwoGate = ExceptionTwoGate(exceptionFourteenGate)
        val secondCheckRequiredGate = SecondCheckRequiredGate(exceptionTwoGate)

        val firstModulusCheckRouter = FirstModulusCheckRouter(
          sortCodeSubstitution,
          secondCheckRequiredGate
        )
        val exceptionTenAccountTransformer = ExceptionTenAccountTransformer(firstModulusCheckRouter)
        val firstExceptionEightAccountTransformer = ExceptionEightAccountTransformer(exceptionTenAccountTransformer) { p -> p.firstWeightRow!! }
        val firstExceptionSevenAccountTransformer = ExceptionSevenAccountTransformer(firstExceptionEightAccountTransformer) { p -> p.firstWeightRow!! }
        val exceptionSixGate = ExceptionSixGate(firstExceptionSevenAccountTransformer)
        return AtLeastOneWeightRowGate(weightRows, exceptionSixGate)
    }
}
