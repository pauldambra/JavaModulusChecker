import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.SortCodeSubstitution;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;

public class SortCodeSubstitutionTests {
    @Test
    public void SortCodesNotInTheTableAreUnchanged() {

        ImmutableMap<String,String> substitutions = ImmutableMap.<String, String>builder()
                                                .put("not_the_sort_code", "12345")
                                                .build();

        SortCodeSubstitution sortCodeSubstitution = new SortCodeSubstitution(substitutions);
        BankAccount originalAccount = BankAccount.Of("012345", "01234567");
        BankAccount account = sortCodeSubstitution.Apply(originalAccount);

        assertThat(account.sortCode, is(equalTo("012345")));
        assertThat(account.accountNumber, is(equalTo("01234567")));
        assertThat(account, is(not(theInstance(originalAccount))));
    }

    @Test
    public void SortCodesInTheTableAreChanged() {

        ImmutableMap<String,String> substitutions = ImmutableMap.<String, String>builder()
                .put("012345", "543210")
                .build();

        SortCodeSubstitution sortCodeSubstitution = new SortCodeSubstitution(substitutions);
        BankAccount originalAccount = BankAccount.Of("012345", "01234567");
        BankAccount account = sortCodeSubstitution.Apply(originalAccount);

        assertThat(account.sortCode, is(equalTo("543210")));
        assertThat(account.accountNumber, is(equalTo("01234567")));
        assertThat(account, is(not(theInstance(originalAccount))));
    }

    @Test
    public void CanLoadFromFileResource() throws IOException {
        SortCodeSubstitution sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt");

        BankAccount ba = BankAccount.Of("938173", "01234567");
        assertThat(sortCodeSubstitution.Apply(ba).sortCode, is(equalTo("938017")));
    }
}
