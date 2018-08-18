package com.github.pauldambra.moduluschecker

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.google.common.base.Charsets
import com.google.common.collect.ImmutableMap
import com.google.common.io.Resources
import java.io.IOException
import java.util.regex.Pattern

class SortCodeSubstitution {

    private val sortCodeSubstitutionTable: ImmutableMap<String, String>

    constructor(sortCodeSubstitutionTable: ImmutableMap<String, String>) {
        this.sortCodeSubstitutionTable = sortCodeSubstitutionTable
    }

    private constructor(sortCodeSubstitutionTable: Map<String, String>) {
        this.sortCodeSubstitutionTable = ImmutableMap.copyOf(sortCodeSubstitutionTable)
    }

    fun apply(bankAccount: BankAccount) =
      if (sortCodeSubstitutionTable.containsKey(bankAccount.sortCode))
        BankAccount.with(sortCodeSubstitutionTable[bankAccount.sortCode]!!, bankAccount.accountNumber)
      else
        BankAccount(bankAccount)

    companion object {

        @Throws(IOException::class)
        fun fromFile(filePath: String): SortCodeSubstitution {

            val url = Resources.getResource(filePath)
            val text = Resources.toString(url, Charsets.UTF_8)

            val substitutions = text.split(Pattern.compile("\r?\n"))
              .filter { it.isNotEmpty() }
              .map { it.split(' ') }
              .filter { it.isNotEmpty() }
              .associate { it[0] to it[1] }

            return SortCodeSubstitution(substitutions)
        }
    }
}
