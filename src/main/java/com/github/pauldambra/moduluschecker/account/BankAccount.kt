package com.github.pauldambra.moduluschecker.account

import com.github.pauldambra.moduluschecker.As
import com.google.common.base.Strings

import java.util.stream.Stream
import kotlin.streams.toList

class BankAccount {

    val sortCode: String
    val accountNumber: String

    private constructor(sortCode: String, accountNumber: String) {
        this.sortCode = sortCode
        this.accountNumber = accountNumber
    }

    constructor(bankAccount: BankAccount) {
        this.sortCode = bankAccount.sortCode
        this.accountNumber = bankAccount.accountNumber
    }

    fun allDigits() = Stream.concat(
      As.integerStream(sortCode),
      As.integerStream(accountNumber))!!

    fun getNumberAt(i: Int) = allDigits().toList()[i]

    override fun toString() = "BankAccount{sortCode='$sortCode, accountNumber='$accountNumber}"

    fun zeroiseUToB(): BankAccount {
        if (getNumberAt(G) == 9) {
            val account = Strings.padStart(accountNumber.substring(2), 8, '0')
            return with("000000", account)
        }
        return with(sortCode, accountNumber)
    }

    companion object {
        const val U = 0
        const val V = 1
        const val W = 2
        const val X = 3
        const val Y = 4
        const val Z = 5
        const val A = 6
        const val B = 7
        const val C = 8
        const val D = 9
        const val E = 10
        const val F = 11
        const val G = 12
        const val H = 13
        const val LLOYDS_EURO_SORT_CODE = "309634"

        fun with(sortCode: String, accountNumber: String): BankAccount {
            val corrected = NonStandardAccounts.corrections(sortCode, accountNumber)
            return BankAccount(corrected[0], corrected[1])
        }
    }

}
