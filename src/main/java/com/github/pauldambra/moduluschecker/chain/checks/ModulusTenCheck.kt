package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ModulusTenCheck {

    fun check(weightRow: WeightRow, bankAccount: BankAccount): Boolean {

        val total = ModulusTotal.calculate(bankAccount, weightRow.weights)

        return total % 10 == 0
    }

}
