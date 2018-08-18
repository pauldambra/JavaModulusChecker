package com.github.pauldambra.moduluschecker.account

import com.github.pauldambra.moduluschecker.toNumberList

class BankAccount {

    var sortCode: String
        private set
    var accountNumber: String
        private set

    constructor(sortCode: String, accountNumber: String) {
        val (sc, an) = NonStandardAccounts.corrections(sortCode, accountNumber)
        this.sortCode = sc
        this.accountNumber = an
    }

    constructor(bankAccount: BankAccount): this(bankAccount.sortCode, bankAccount.accountNumber)

    fun allDigits() = sortCode.toNumberList() + accountNumber.toNumberList()

    fun getNumberAt(i: Int) = allDigits()[i]

    fun zeroiseUToB(): BankAccount {
        if (getNumberAt(G) == 9) {
            val an = accountNumber.substring(2).padStart(8, '0')
            return BankAccount("000000", an)
        }
        return BankAccount(sortCode, accountNumber)
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
    }

}
