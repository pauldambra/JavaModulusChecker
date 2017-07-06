package com.dambra.paul.moduluschecker.Account;

import com.dambra.paul.moduluschecker.As;
import com.google.common.base.Strings;

import java.util.stream.Stream;

public class BankAccount {
    public static final int U = 0, V = 1, W = 2, X = 3, Y = 4, Z = 5,
            A = 6, B = 7, C = 8, D = 9, E = 10, F = 11, G = 12, H = 13;
    public static final String LLOYDS_EURO_SORT_CODE = "309634";

    public final String sortCode;
    public final String accountNumber;

    public BankAccount(String sortCode, String accountNumber) {
        String[] corrected = NonStandardAccounts.corrections(sortCode, accountNumber);

        this.sortCode = corrected[0];
        this.accountNumber = corrected[1];
    }

    public BankAccount(BankAccount bankAccount) {
        this.sortCode = bankAccount.sortCode;
        this.accountNumber = bankAccount.accountNumber;
    }

    public Stream<Integer> allDigits() {
        return Stream.concat(
                As.integerStream(sortCode),
                As.integerStream(accountNumber));
    }

    public int getNumberAt(int i) {
        return allDigits().toArray(Integer[]::new)[i];
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "sortCode='" + sortCode + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }

    public BankAccount zeroiseUToB() {
        if (getNumberAt(G) == 9) {
            String account = Strings.padStart(accountNumber.substring(2), 8, '0');
            return new BankAccount("000000", account);
        }
        return new BankAccount(sortCode, accountNumber);
    }

    private static class NonStandardAccounts {
        public static String[] corrections(String sortCode, String accountNumber) {

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

    // I didn't log where I got this data... is it correct?
    // todo allow external hookup of ISCD? Can't find a free version
    private static class SortCode {
        static boolean IsCooperativeBankSortCode(String sortCode) {
            return sortCode.startsWith("08") || sortCode.startsWith("839");
        }

        static boolean IsNatWestSortCode(String sortCode) {
            return sortCode.startsWith("600")
                    || sortCode.startsWith("606")
                    || sortCode.startsWith("601")
                    || sortCode.startsWith("609")
                    || sortCode.startsWith("830")
                    || sortCode.startsWith("602");
        }
    }
}
