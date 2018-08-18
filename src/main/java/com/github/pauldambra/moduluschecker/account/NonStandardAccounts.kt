package com.github.pauldambra.moduluschecker.account

internal object NonStandardAccounts {
    fun corrections(sortCode: String, accountNumber: String): Array<String> {
        var accountNumber = accountNumber

        accountNumber = correctForNatWest(sortCode, accountNumber)
        accountNumber = correctForCoop(sortCode, accountNumber)
        accountNumber = correctForShortAccountNumber(accountNumber)

        return correctForSantander(sortCode, accountNumber)
    }

    private fun correctForShortAccountNumber(accountNumber: String) = accountNumber.padStart(8, '0')

    /**
     * Replace the last digit of the sorting code with
     * the first digit of the account number, then use
     * the last eight digits of the account number only.
     */
    private fun correctForSantander(sortCode: String, accountNumber: String) =
      if (accountNumber.length != 9) {
          arrayOf(sortCode, accountNumber)
      } else {
          arrayOf(sortCode.substring(0, 5) + accountNumber.substring(0, 1), accountNumber.substring(1, 9))
      }

    /**
     * use the first eight digits only
     */
    private fun correctForCoop(sortCode: String, accountNumber: String) =
      if (SortCode.isCooperativeBankSortCode(sortCode))
          accountNumber.substring(0, 8)
      else
          accountNumber

    /**
     * NatWest have ten digit account numbers
     *
     * Use the last eight digits only. If there is a hyphen
     * in the account number between the second and
     * third numbers this should be ignored.
     */
    private fun correctForNatWest(sortCode: String, accountNumber: String): String {
        return if (!SortCode.IsNatWestSortCode(sortCode)) {
            accountNumber
        } else {
            accountNumber
              .replace("-", "")
              .takeLast(8)
        }
    }
}
