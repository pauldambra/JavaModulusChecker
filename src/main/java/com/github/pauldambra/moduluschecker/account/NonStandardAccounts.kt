package com.github.pauldambra.moduluschecker.account

internal object NonStandardAccounts {

    // I didn't log where I got this data... is it correct?
    // todo allow external hookup of ISCD? Can't find a free version
    private fun isCooperativeBankSortCode(sortCode: String) =
      sortCode.startsWith("08") || sortCode.startsWith("839")

    private fun isNatWestSortCode(sortCode: String) =
      sortCode.startsWith("600")
        || sortCode.startsWith("606")
        || sortCode.startsWith("601")
        || sortCode.startsWith("609")
        || sortCode.startsWith("830")
        || sortCode.startsWith("602")

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
      if (isCooperativeBankSortCode(sortCode))
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
        return if (isNatWestSortCode(sortCode)) {
            accountNumber
              .replace("-", "")
              .takeLast(8)
        } else {
            accountNumber
        }
    }
}
