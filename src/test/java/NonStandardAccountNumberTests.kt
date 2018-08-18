

import com.github.pauldambra.moduluschecker.account.BankAccount
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class NonStandardAccountNumberTests {

    @Test
    fun CanCreateNatWestAccounts() {
        val account = BankAccount.with("601123", "0123456789")

        assertThat(account.accountNumber, `is`(equalTo("23456789")))
    }

    @Test
    fun CanCreateHyphenatedNatWestAccounts() {
        val account = BankAccount.with("602345", "01-23456789")

        assertThat(account.accountNumber, `is`(equalTo("23456789")))
    }

    @Test
    fun CanCreateCoopBankAccounts() {
        val account = BankAccount.with("089286", "1234567890")

        assertThat(account.accountNumber, `is`(equalTo("12345678")))
    }

    @Test
    fun CanCreateSantanderAccounts() {
        val account = BankAccount.with("123456", "123456789")

        assertThat(account.sortCode, `is`(equalTo("123451")))
        assertThat(account.accountNumber, `is`(equalTo("23456789")))
    }

    @Test
    fun CanCreateSevenDigitAccounts() {
        val account = BankAccount.with("123456", "1234567")

        assertThat(account.sortCode, `is`(equalTo("123456")))
        assertThat(account.accountNumber, `is`(equalTo("01234567")))
    }

    @Test
    fun CanCreateSixDigitAccounts() {
        val account = BankAccount.with("123456", "123456")

        assertThat(account.sortCode, `is`(equalTo("123456")))
        assertThat(account.accountNumber, `is`(equalTo("00123456")))
    }
}
