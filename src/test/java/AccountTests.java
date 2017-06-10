import com.dambra.paul.moduluschecker.BankAccount;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AccountTests {
    @Test
    public void CanGetSortCodeAndAccountNumberForModulusChecks() {
        String sc = "012345";
        String an = "01234567";
        BankAccount account = new BankAccount(sc, an);
        List<Integer> expected = ImmutableList.of(0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7);
        assertThat(account.allDigits(), is(equalTo(expected)));
    }
}
