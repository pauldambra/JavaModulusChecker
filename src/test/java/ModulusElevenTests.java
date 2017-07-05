import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusAlgorithm;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;
import com.dambra.paul.moduluschecker.chain.checks.ModulusElevenCheck;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ModulusElevenTests {
    @Test
    public void CanRunModulusElevenCheck() {
        String sc = "107999";
        String an = "88837491";
        WeightRow row = new WeightRow(
                ModulusAlgorithm.MOD11,
                ImmutableList.of(0, 0, 0, 0, 0, 0, 8, 7, 6, 5, 4, 3, 2, 1),
                Optional.empty()
        );

        ModulusCheckParams params = new ModulusCheckParams(
                new BankAccount(sc, an),
                Optional.of(row),
                Optional.empty(),
                Optional.empty());
        ModulusElevenCheck checker = new ModulusElevenCheck();

        Boolean result = checker.check(params, x -> Optional.ofNullable(
                WeightRow.copy(x.firstWeightRow.orElse(null))).get());

        assertThat(result, is(equalTo(true)));
    }
}
