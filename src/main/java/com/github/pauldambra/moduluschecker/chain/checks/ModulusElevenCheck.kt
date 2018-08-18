package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusElevenWeightsTransformer
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ModulusElevenCheck {

    fun check(params: ModulusCheckParams, weightRow: WeightRow): Boolean {

        val weights = ModulusElevenWeightsTransformer.checkForExceptionTwoAndNine(params, weightRow)

        val total = ModulusTotal.calculate(params.account, weights)
        val remainder = total % 11

        return if (weightRow.isException(4)) {
            remainder == exceptionFourCheckDigit(params)
        } else {
            if (remainder == 0) {
                true
            } else {
                LloydsAlternateModulusElevenCheck().check(params, weightRow)
            }

        }
    }

    private fun exceptionFourCheckDigit(params: ModulusCheckParams): Int {
        val g = params.account.getNumberAt(BankAccount.G)
        val h = params.account.getNumberAt(BankAccount.H)
        return Integer.parseInt(String.format("%s%s", g, h))
    }

}
