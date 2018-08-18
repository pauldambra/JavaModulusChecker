

import com.github.pauldambra.moduluschecker.account.BankAccount
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class NonStandardAccountNumberTests {

    @Test
    fun canCreateNatWestAccounts() {
        val account = BankAccount("601123", "0123456789")

        assertThat(account.accountNumber, `is`(equalTo("23456789")))
    }

    @Test
    fun canCreateHyphenatedNatWestAccounts() {
        val account = BankAccount("602345", "01-23456789")

        assertThat(account.accountNumber, `is`(equalTo("23456789")))
    }

    @Test
    fun canCreateCoopBankAccounts() {
        val account = BankAccount("089286", "1234567890")

        assertThat(account.accountNumber, `is`(equalTo("12345678")))
    }

    @Test
    fun canCreateSantanderAccounts() {
        val account = BankAccount("123456", "123456789")

        assertThat(account.sortCode, `is`(equalTo("123451")))
        assertThat(account.accountNumber, `is`(equalTo("23456789")))
    }

    @Test
    fun canCreateSevenDigitAccounts() {
        val account = BankAccount("123456", "1234567")

        assertThat(account.sortCode, `is`(equalTo("123456")))
        assertThat(account.accountNumber, `is`(equalTo("01234567")))
    }

    @Test
    fun canCreateSixDigitAccounts() {
        val account = BankAccount("123456", "123456")

        assertThat(account.sortCode, `is`(equalTo("123456")))
        assertThat(account.accountNumber, `is`(equalTo("00123456")))
    }
}
