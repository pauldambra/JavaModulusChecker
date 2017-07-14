package com.github.pauldambra.moduluschecker.Account;

import com.google.common.base.Strings;

class NonStandardAccounts {
    static String[] corrections(String sortCode, String accountNumber) {

        accountNumber = CorrectForNatWest(sortCode, accountNumber);
        accountNumber = CorrectForCoop(sortCode, accountNumber);
        accountNumber = correctForShortAccountNumber(accountNumber);

        return CorrectForSantander(sortCode, accountNumber);
    }

    /**
     * pad left with zeros until 8 in length
     */
    private static String correctForShortAccountNumber(String accountNumber) {
        return Strings.padStart(accountNumber, 8, '0');
    }

    /**
     * Replace the last digit of the sorting code with
     * the first digit of the account number, then use
     * the last eight digits of the account number only.
     */
    private static String[] CorrectForSantander(String sortCode, String accountNumber) {
        if (accountNumber.length()!=9) {
            return new String[] {sortCode, accountNumber};
        }

        return new String[]{
                sortCode.substring(0, 5) + accountNumber.substring(0, 1),
                accountNumber.substring(1, 9)
        };
    }

    /**
     * use the first eight digits only
     */
    private static String CorrectForCoop(String sortCode, String accountNumber) {
        return SortCode.IsCooperativeBankSortCode(sortCode)
                ? accountNumber.substring(0, 8)
                : accountNumber;
    }

    /**
     * NatWest have ten digit account numbers
     *
     * Use the last eight digits only. If there is a hyphen
     * in the account number between the second and
     * third numbers this should be ignored.
     */
    private static String CorrectForNatWest(String sortCode, String accountNumber) {
        if (!SortCode.IsNatWestSortCode(sortCode)) {
            return accountNumber;
        }

        accountNumber = accountNumber.replaceAll("[\\D]", "");
        return accountNumber.substring(accountNumber.length() - 8);
    }
}
