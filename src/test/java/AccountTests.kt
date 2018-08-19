

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class AccountTests {
    @Test
    fun canGetSortCodeAndAccountNumberForModulusChecks() {
        val sc = "012345"
        val an = "01234567"
        val account = BankAccount(sc, an)
        val expected = listOf(0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7)
        assert.that(account.allDigits(), equalTo(expected))
    }

    @Test
    fun canUseAccountNotationToGetNumbersFromAccount() {
        val sc = "654321"
        val an = "98765432"
        val account = BankAccount(sc, an)

        assert.that(account.getNumberAt(BankAccount.U), equalTo(6))
        assert.that(account.getNumberAt(BankAccount.V), equalTo(5))
        assert.that(account.getNumberAt(BankAccount.W), equalTo(4))
        assert.that(account.getNumberAt(BankAccount.X), equalTo(3))
        assert.that(account.getNumberAt(BankAccount.Y), equalTo(2))
        assert.that(account.getNumberAt(BankAccount.Z), equalTo(1))
        assert.that(account.getNumberAt(BankAccount.A), equalTo(9))
        assert.that(account.getNumberAt(BankAccount.B), equalTo(8))
        assert.that(account.getNumberAt(BankAccount.C), equalTo(7))
        assert.that(account.getNumberAt(BankAccount.D), equalTo(6))
        assert.that(account.getNumberAt(BankAccount.E), equalTo(5))
        assert.that(account.getNumberAt(BankAccount.F), equalTo(4))
        assert.that(account.getNumberAt(BankAccount.G), equalTo(3))
        assert.that(account.getNumberAt(BankAccount.H), equalTo(2))
    }

    @Test
    fun canAvoidZeroiseForExceptionSeven() {
        val sc = "654321"
        val an = "98765432"
        val account = BankAccount(sc, an).zeroiseUToB()

        assert.that(account.getNumberAt(BankAccount.U), equalTo(6))
        assert.that(account.getNumberAt(BankAccount.V), equalTo(5))
        assert.that(account.getNumberAt(BankAccount.W), equalTo(4))
        assert.that(account.getNumberAt(BankAccount.X), equalTo(3))
        assert.that(account.getNumberAt(BankAccount.Y), equalTo(2))
        assert.that(account.getNumberAt(BankAccount.Z), equalTo(1))
        assert.that(account.getNumberAt(BankAccount.A), equalTo(9))
        assert.that(account.getNumberAt(BankAccount.B), equalTo(8))
        assert.that(account.getNumberAt(BankAccount.C), equalTo(7))
        assert.that(account.getNumberAt(BankAccount.D), equalTo(6))
        assert.that(account.getNumberAt(BankAccount.E), equalTo(5))
        assert.that(account.getNumberAt(BankAccount.F), equalTo(4))
        assert.that(account.getNumberAt(BankAccount.G), equalTo(3))
        assert.that(account.getNumberAt(BankAccount.H), equalTo(2))
    }

    @Test
    fun canZeroiseForExceptionSeven() {
        val sc = "654321"
        val an = "98765492"
        val account = BankAccount(sc, an).zeroiseUToB()

        assert.that(account.getNumberAt(BankAccount.U), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.V), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.W), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.X), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.Y), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.Z), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.A), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.B), equalTo(0))
        assert.that(account.getNumberAt(BankAccount.C), equalTo(7))
        assert.that(account.getNumberAt(BankAccount.D), equalTo(6))
        assert.that(account.getNumberAt(BankAccount.E), equalTo(5))
        assert.that(account.getNumberAt(BankAccount.F), equalTo(4))
        assert.that(account.getNumberAt(BankAccount.G), equalTo(9))
        assert.that(account.getNumberAt(BankAccount.H), equalTo(2))
    }

}
