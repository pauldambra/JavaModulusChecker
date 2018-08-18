package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.SortCodeSubstitution
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionFiveModulusElevenCheck(private val sortCodeSubstitution: SortCodeSubstitution) {

    fun check(params: ModulusCheckParams, weightRow: WeightRow): Boolean {

        val bankAccount = sortCodeSubstitution.apply(params.account)
        val updatedParams = params.copy(account = bankAccount)

        val total = ModulusTotal.calculate(updatedParams.account, weightRow.weights)
        val remainder = total % 11

        val checkDigit = updatedParams.account.getNumberAt(BankAccount.G)

        return if (remainder == 0 && checkDigit == 0) {
            true
        } else if (remainder == 1) {
            false
        } else {
            11 - remainder == checkDigit
        }

    }
}
