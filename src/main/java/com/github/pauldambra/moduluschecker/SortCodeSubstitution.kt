package com.github.pauldambra.moduluschecker

import com.github.pauldambra.moduluschecker.account.BankAccount
import java.io.IOException
import java.util.regex.Pattern

class SortCodeSubstitution(private val sortCodeSubstitutionTable: Map<String, String>) {

    fun apply(bankAccount: BankAccount) =
      if (sortCodeSubstitutionTable.containsKey(bankAccount.sortCode))
          BankAccount(sortCodeSubstitutionTable[bankAccount.sortCode]!!, bankAccount.accountNumber)
      else
          BankAccount(bankAccount)

    companion object {

        @Throws(IOException::class)
        fun fromFile(filePath: String): SortCodeSubstitution {

            val substitutions =
              this::class.java.classLoader.getResource(filePath).readText()
                .split(Pattern.compile("\r?\n"))
                .filter { it.isNotEmpty() }
                .map { it.split(' ') }
                .filter { it.isNotEmpty() }
                .associate { it[0] to it[1] }

            return SortCodeSubstitution(substitutions)
        }
    }
}
