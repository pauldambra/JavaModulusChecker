package com.github.pauldambra.moduluschecker.account

import com.google.common.base.Strings

internal object NonStandardAccounts {
    fun corrections(sortCode: String, accountNumber: String): Array<String> {
        var accountNumber = accountNumber

        accountNumber = CorrectForNatWest(sortCode, accountNumber)
        accountNumber = CorrectForCoop(sortCode, accountNumber)
        accountNumber = correctForShortAccountNumber(accountNumber)

        return CorrectForSantander(sortCode, accountNumber)
    }

    /**
     * pad left with zeros until 8 in length
     */
    private fun correctForShortAccountNumber(accountNumber: String): String {
        return Strings.padStart(accountNumber, 8, '0')
    }

    /**
     * Replace the last digit of the sorting code with
     * the first digit of the account number, then use
     * the last eight digits of the account number only.
     */
    private fun CorrectForSantander(sortCode: String, accountNumber: String): Array<String> {
        return if (accountNumber.length != 9) {
            arrayOf(sortCode, accountNumber)
        } else arrayOf(sortCode.substring(0, 5) + accountNumber.substring(0, 1), accountNumber.substring(1, 9))

    }

    /**
     * use the first eight digits only
     */
    private fun CorrectForCoop(sortCode: String, accountNumber: String): String {
        return if (SortCode.IsCooperativeBankSortCode(sortCode))
            accountNumber.substring(0, 8)
        else
            accountNumber
    }

    /**
     * NatWest have ten digit account numbers
     *
     * Use the last eight digits only. If there is a hyphen
     * in the account number between the second and
     * third numbers this should be ignored.
     */
    private fun CorrectForNatWest(sortCode: String, accountNumber: String): String {
        var accountNumber = accountNumber
        if (!SortCode.IsNatWestSortCode(sortCode)) {
            return accountNumber
        }

        accountNumber = accountNumber.replace("[\\D]".toRegex(), "")
        return accountNumber.substring(accountNumber.length - 8)
    }
}
