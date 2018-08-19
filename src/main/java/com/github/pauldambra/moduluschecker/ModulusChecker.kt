package com.github.pauldambra.moduluschecker

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows

import java.io.IOException

class ModulusChecker @Throws(IOException::class)
constructor() {
    private val sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt")

    private val weightRows = ModulusWeightRows.fromFile("file/valacdos.txt")

    fun checkBankAccount(sortCode: String, accountNumber: String): Boolean {
        val account = BankAccount(sortCode, accountNumber)
        val params = ModulusCheckParams(account)

        return ModulusCheckingChain
          .create(weightRows, sortCodeSubstitution)
          .check(params)
          .processResults()
    }

}
