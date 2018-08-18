package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusElevenWeightsTransformer
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ModulusElevenCheck {

    fun check(params: ModulusCheckParams, rowSelector: (mcp: ModulusCheckParams) ->WeightRow): Boolean {
        val selectedRow = rowSelector(params)

        val weights = ModulusElevenWeightsTransformer.checkForExceptionTwoAndNine(params, selectedRow)

        val total = ModulusTotal.calculate(params.account, weights)
        val remainder = total % 11

        return if (selectedRow.isException(4)) {
            remainder == exceptionFourCheckDigit(params)
        } else {
            val result = remainder == 0
            if (result) {
                true
            } else LloydsAlternateModulusElevenCheck().check(params, rowSelector)

        }
    }

    private fun exceptionFourCheckDigit(params: ModulusCheckParams): Int {
        val g = params.account.getNumberAt(BankAccount.G)
        val h = params.account.getNumberAt(BankAccount.H)
        return Integer.parseInt(String.format("%s%s", g, h))
    }

}
