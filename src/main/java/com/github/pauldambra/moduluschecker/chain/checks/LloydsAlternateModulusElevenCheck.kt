package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

import java.util.function.Function

class LloydsAlternateModulusElevenCheck {
    fun check(params: ModulusCheckParams, rowSelector: (mcp: ModulusCheckParams) ->WeightRow): Boolean {
        val selectedRow = rowSelector(params)

        if (!WeightRow.isExceptionTwoAndNine(
            params.firstWeightRow,
            params.secondWeightRow)) {
            return false
        }

        val account = BankAccount.with(BankAccount.LLOYDS_EURO_SORT_CODE, params.account.accountNumber)

        val total = ModulusTotal.calculate(account, selectedRow.weights)
        return total % 11 == 0
    }
}
