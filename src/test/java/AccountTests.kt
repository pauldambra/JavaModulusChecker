

import com.github.pauldambra.moduluschecker.account.BankAccount
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class AccountTests {
    @Test
    fun canGetSortCodeAndAccountNumberForModulusChecks() {
        val sc = "012345"
        val an = "01234567"
        val account = BankAccount(sc, an)
        val expected = listOf(0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7)
        assertThat(account.allDigits(), `is`(equalTo(expected)))
    }

    @Test
    fun canUseAccountNotationToGetNumbersFromAccount() {
        val sc = "654321"
        val an = "98765432"
        val account = BankAccount(sc, an)

        assertThat(account.getNumberAt(BankAccount.U), `is`(equalTo(6)))
        assertThat(account.getNumberAt(BankAccount.V), `is`(equalTo(5)))
        assertThat(account.getNumberAt(BankAccount.W), `is`(equalTo(4)))
        assertThat(account.getNumberAt(BankAccount.X), `is`(equalTo(3)))
        assertThat(account.getNumberAt(BankAccount.Y), `is`(equalTo(2)))
        assertThat(account.getNumberAt(BankAccount.Z), `is`(equalTo(1)))
        assertThat(account.getNumberAt(BankAccount.A), `is`(equalTo(9)))
        assertThat(account.getNumberAt(BankAccount.B), `is`(equalTo(8)))
        assertThat(account.getNumberAt(BankAccount.C), `is`(equalTo(7)))
        assertThat(account.getNumberAt(BankAccount.D), `is`(equalTo(6)))
        assertThat(account.getNumberAt(BankAccount.E), `is`(equalTo(5)))
        assertThat(account.getNumberAt(BankAccount.F), `is`(equalTo(4)))
        assertThat(account.getNumberAt(BankAccount.G), `is`(equalTo(3)))
        assertThat(account.getNumberAt(BankAccount.H), `is`(equalTo(2)))
    }

    @Test
    fun canAvoidZeroiseForExceptionSeven() {
        val sc = "654321"
        val an = "98765432"
        val account = BankAccount(sc, an).zeroiseUToB()

        assertThat(account.getNumberAt(BankAccount.U), `is`(equalTo(6)))
        assertThat(account.getNumberAt(BankAccount.V), `is`(equalTo(5)))
        assertThat(account.getNumberAt(BankAccount.W), `is`(equalTo(4)))
        assertThat(account.getNumberAt(BankAccount.X), `is`(equalTo(3)))
        assertThat(account.getNumberAt(BankAccount.Y), `is`(equalTo(2)))
        assertThat(account.getNumberAt(BankAccount.Z), `is`(equalTo(1)))
        assertThat(account.getNumberAt(BankAccount.A), `is`(equalTo(9)))
        assertThat(account.getNumberAt(BankAccount.B), `is`(equalTo(8)))
        assertThat(account.getNumberAt(BankAccount.C), `is`(equalTo(7)))
        assertThat(account.getNumberAt(BankAccount.D), `is`(equalTo(6)))
        assertThat(account.getNumberAt(BankAccount.E), `is`(equalTo(5)))
        assertThat(account.getNumberAt(BankAccount.F), `is`(equalTo(4)))
        assertThat(account.getNumberAt(BankAccount.G), `is`(equalTo(3)))
        assertThat(account.getNumberAt(BankAccount.H), `is`(equalTo(2)))
    }

    @Test
    fun canZeroiseForExceptionSeven() {
        val sc = "654321"
        val an = "98765492"
        val account = BankAccount(sc, an).zeroiseUToB()

        assertThat(account.getNumberAt(BankAccount.U), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.V), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.W), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.X), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.Y), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.Z), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.A), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.B), `is`(equalTo(0)))
        assertThat(account.getNumberAt(BankAccount.C), `is`(equalTo(7)))
        assertThat(account.getNumberAt(BankAccount.D), `is`(equalTo(6)))
        assertThat(account.getNumberAt(BankAccount.E), `is`(equalTo(5)))
        assertThat(account.getNumberAt(BankAccount.F), `is`(equalTo(4)))
        assertThat(account.getNumberAt(BankAccount.G), `is`(equalTo(9)))
        assertThat(account.getNumberAt(BankAccount.H), `is`(equalTo(2)))
    }

}
