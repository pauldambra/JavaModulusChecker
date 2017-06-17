import com.dambra.paul.moduluschecker.*;
import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.chain.DoubleAlternateCheck;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DoubleAlternateTests {

    @Test
    public void CanRunDoubleAlternateCheck() {
        String sc = "499273";
        String an = "12345678";
        WeightRow row = new WeightRow(
                ModulusAlgorithm.DOUBLE_ALTERNATE,
                ImmutableList.of(2,1,2,1,2,1,2,1,2,1,2,1,2,1),
                Optional.empty()
                );

        ModulusCheckParams params = new ModulusCheckParams(
                new BankAccount(sc, an),
                Optional.of(row),
                Optional.empty(),
                Optional.empty());
        DoubleAlternateCheck checker = new DoubleAlternateCheck();

        Boolean result = checker.check(params, x -> x.getFirstWeightRow().get());

        assertThat(result, is(equalTo(true)));
    }
}
