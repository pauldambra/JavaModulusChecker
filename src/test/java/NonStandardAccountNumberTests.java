import com.github.pauldambra.moduluschecker.Account.BankAccount;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class NonStandardAccountNumberTests {

    @Test
    public void CanCreateNatWestAccounts() {
        BankAccount account = BankAccount.Of("601123", "0123456789");

        assertThat(account.accountNumber, is(equalTo("23456789")));
    }

    @Test
    public void CanCreateHyphenatedNatWestAccounts() {
        BankAccount account = BankAccount.Of("602345", "01-23456789");

        assertThat(account.accountNumber, is(equalTo("23456789")));
    }

    @Test
    public void CanCreateCoopBankAccounts() {
        BankAccount account = BankAccount.Of("089286", "1234567890");

        assertThat(account.accountNumber, is(equalTo("12345678")));
    }

    @Test
    public void CanCreateSantanderAccounts() {
        BankAccount account = BankAccount.Of("123456", "123456789");

        assertThat(account.sortCode, is(equalTo("123451")));
        assertThat(account.accountNumber, is(equalTo("23456789")));
    }

    @Test
    public void CanCreateSevenDigitAccounts() {
        BankAccount account = BankAccount.Of("123456", "1234567");

        assertThat(account.sortCode, is(equalTo("123456")));
        assertThat(account.accountNumber, is(equalTo("01234567")));
    }

    @Test
    public void CanCreateSixDigitAccounts() {
        BankAccount account = BankAccount.Of("123456", "123456");

        assertThat(account.sortCode, is(equalTo("123456")));
        assertThat(account.accountNumber, is(equalTo("00123456")));
    }
}
