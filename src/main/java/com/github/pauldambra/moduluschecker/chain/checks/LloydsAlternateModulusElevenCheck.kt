package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class LloydsAlternateModulusElevenCheck {
    fun check(params: ModulusCheckParams, weightRow: WeightRow): Boolean {

        if (!WeightRow.isExceptionTwoAndNine(
            params.firstWeightRow,
            params.secondWeightRow)) {
            return false
        }

        val account = BankAccount(BankAccount.LLOYDS_EURO_SORT_CODE, params.account.accountNumber)

        val total = ModulusTotal.calculate(account, weightRow.weights)
        return total % 11 == 0
    }
}
