

import com.github.pauldambra.moduluschecker.account.BankAccount
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import java.util.stream.Stream

class AccountTests {
    @Test
    fun CanGetSortCodeAndAccountNumberForModulusChecks() {
        val sc = "012345"
        val an = "01234567"
        val account = BankAccount.with(sc, an)
        val expected = Stream.of(0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7)
        Assert.thatStreamEquals(account.allDigits(), expected)
    }

    @Test
    fun CanUseAccountNotationToGetNumbersFromAccount() {
        val sc = "654321"
        val an = "98765432"
        val account = BankAccount.with(sc, an)

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
    fun CanAvoidZeroiseForExceptionSeven() {
        val sc = "654321"
        val an = "98765432"
        val account = BankAccount.with(sc, an).zeroiseUToB()

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
    fun CanZeroiseForExceptionSeven() {
        val sc = "654321"
        val an = "98765492"
        val account = BankAccount.with(sc, an).zeroiseUToB()

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
