import com.dambra.paul.moduluschecker.Account.BankAccount;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AccountTests {
    @Test
    public void CanGetSortCodeAndAccountNumberForModulusChecks() {
        String sc = "012345";
        String an = "01234567";
        BankAccount account = new BankAccount(sc, an);
        Stream<Integer> expected = Stream.of(0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7);
        Assert.thatStreamEquals(account.allDigits(), expected);
    }

    @Test
    public void CanUseAccountNotationToGetNumbersFromAccount() {
        String sc = "654321";
        String an = "98765432";
        BankAccount account = new BankAccount(sc, an);

        assertThat(account.getNumberAt(BankAccount.U), is(equalTo(6)));
        assertThat(account.getNumberAt(BankAccount.V), is(equalTo(5)));
        assertThat(account.getNumberAt(BankAccount.W), is(equalTo(4)));
        assertThat(account.getNumberAt(BankAccount.X), is(equalTo(3)));
        assertThat(account.getNumberAt(BankAccount.Y), is(equalTo(2)));
        assertThat(account.getNumberAt(BankAccount.Z), is(equalTo(1)));
        assertThat(account.getNumberAt(BankAccount.A), is(equalTo(9)));
        assertThat(account.getNumberAt(BankAccount.B), is(equalTo(8)));
        assertThat(account.getNumberAt(BankAccount.C), is(equalTo(7)));
        assertThat(account.getNumberAt(BankAccount.D), is(equalTo(6)));
        assertThat(account.getNumberAt(BankAccount.E), is(equalTo(5)));
        assertThat(account.getNumberAt(BankAccount.F), is(equalTo(4)));
        assertThat(account.getNumberAt(BankAccount.G), is(equalTo(3)));
        assertThat(account.getNumberAt(BankAccount.H), is(equalTo(2)));
    }

    @Test
    public void CanAvoidZeroiseForExceptionSeven() {
        String sc = "654321";
        String an = "98765432";
        BankAccount account = new BankAccount(sc, an).zeroiseUToB();

        assertThat(account.getNumberAt(BankAccount.U), is(equalTo(6)));
        assertThat(account.getNumberAt(BankAccount.V), is(equalTo(5)));
        assertThat(account.getNumberAt(BankAccount.W), is(equalTo(4)));
        assertThat(account.getNumberAt(BankAccount.X), is(equalTo(3)));
        assertThat(account.getNumberAt(BankAccount.Y), is(equalTo(2)));
        assertThat(account.getNumberAt(BankAccount.Z), is(equalTo(1)));
        assertThat(account.getNumberAt(BankAccount.A), is(equalTo(9)));
        assertThat(account.getNumberAt(BankAccount.B), is(equalTo(8)));
        assertThat(account.getNumberAt(BankAccount.C), is(equalTo(7)));
        assertThat(account.getNumberAt(BankAccount.D), is(equalTo(6)));
        assertThat(account.getNumberAt(BankAccount.E), is(equalTo(5)));
        assertThat(account.getNumberAt(BankAccount.F), is(equalTo(4)));
        assertThat(account.getNumberAt(BankAccount.G), is(equalTo(3)));
        assertThat(account.getNumberAt(BankAccount.H), is(equalTo(2)));
    }

    @Test
    public void CanZeroiseForExceptionSeven() {
        String sc = "654321";
        String an = "98765492";
        BankAccount account = new BankAccount(sc, an).zeroiseUToB();

        assertThat(account.getNumberAt(BankAccount.U), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.V), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.W), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.X), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.Y), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.Z), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.A), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.B), is(equalTo(0)));
        assertThat(account.getNumberAt(BankAccount.C), is(equalTo(7)));
        assertThat(account.getNumberAt(BankAccount.D), is(equalTo(6)));
        assertThat(account.getNumberAt(BankAccount.E), is(equalTo(5)));
        assertThat(account.getNumberAt(BankAccount.F), is(equalTo(4)));
        assertThat(account.getNumberAt(BankAccount.G), is(equalTo(9)));
        assertThat(account.getNumberAt(BankAccount.H), is(equalTo(2)));
    }

}
