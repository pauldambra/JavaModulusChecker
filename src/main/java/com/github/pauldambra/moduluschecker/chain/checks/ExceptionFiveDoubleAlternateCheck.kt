package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.SortCodeSubstitution
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionFiveDoubleAlternateCheck(private val sortCodeSubstitution: SortCodeSubstitution) {

    fun check(
      params: ModulusCheckParams,
      rowSelector: (mcp: ModulusCheckParams) -> WeightRow
    ): Boolean {
        val selectedRow = rowSelector(params)

        val bankAccount = sortCodeSubstitution.apply(params.account)
        val updatedParams = params.copy(account = bankAccount)

        val total = ModulusTotal.calculateDoubleAlternate(updatedParams.account, selectedRow.weights)
        val remainder = total % 10
        val checkDigit = updatedParams.account.getNumberAt(BankAccount.H)

        return if (remainder == 0 && checkDigit == 0) {
            true
        } else 10 - remainder == checkDigit

    }
}
