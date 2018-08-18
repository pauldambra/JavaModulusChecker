package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow


class DoubleAlternateCheck {
    fun check(
      bankAccount: BankAccount,
      weightRow: WeightRow): Boolean {

        val exceptionModifier = if (weightRow.hasException(1)) 27 else 0

        var total = ModulusTotal.calculateDoubleAlternate(bankAccount, weightRow.weights) + exceptionModifier

        return total % 10 == 0
    }
}