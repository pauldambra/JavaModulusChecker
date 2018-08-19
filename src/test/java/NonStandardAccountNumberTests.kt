

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class NonStandardAccountNumberTests {

    @Test
    fun canCreateNatWestAccounts() {
        val account = BankAccount("601123", "0123456789")

        assert.that(account.accountNumber, equalTo("23456789"))
    }

    @Test
    fun canCreateHyphenatedNatWestAccounts() {
        val account = BankAccount("602345", "01-23456789")

        assert.that(account.accountNumber, equalTo("23456789"))
    }

    @Test
    fun canCreateCoopBankAccounts() {
        val account = BankAccount("089286", "1234567890")

        assert.that(account.accountNumber, equalTo("12345678"))
    }

    @Test
    fun canCreateSantanderAccounts() {
        val account = BankAccount("123456", "123456789")

        assert.that(account.sortCode, equalTo("123451"))
        assert.that(account.accountNumber, equalTo("23456789"))
    }

    @Test
    fun canCreateSevenDigitAccounts() {
        val account = BankAccount("123456", "1234567")

        assert.that(account.sortCode, equalTo("123456"))
        assert.that(account.accountNumber, equalTo("01234567"))
    }

    @Test
    fun canCreateSixDigitAccounts() {
        val account = BankAccount("123456", "123456")

        assert.that(account.sortCode, equalTo("123456"))
        assert.that(account.accountNumber, equalTo("00123456"))
    }
}
