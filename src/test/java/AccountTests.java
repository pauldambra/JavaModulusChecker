import com.dambra.paul.moduluschecker.BankAccount;

import org.junit.Test;

import java.util.stream.Stream;

public class AccountTests {
    @Test
    public void CanGetSortCodeAndAccountNumberForModulusChecks() {
        String sc = "012345";
        String an = "01234567";
        BankAccount account = new BankAccount(sc, an);
        Stream<Integer> expected = Stream.of(0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7);
        Assert.thatStreamEquals(account.allDigits(), expected);
    }
}
