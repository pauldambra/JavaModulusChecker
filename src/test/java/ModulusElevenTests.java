import com.dambra.paul.moduluschecker.BankAccount;
import com.dambra.paul.moduluschecker.ModulusAlgorithm;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.dambra.paul.moduluschecker.WeightRow;
import com.dambra.paul.moduluschecker.checks.ModulusEleven;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

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
                Stream.of(0, 0, 0, 0, 0, 0, 8, 7, 6, 5, 4, 3, 2, 1),
                Optional.empty()
        );

        ModulusCheckParams params = new ModulusCheckParams(
                new BankAccount(sc, an),
                Optional.of(ImmutableList.of(row)));
        ModulusEleven checker = new ModulusEleven();

        Boolean result = checker.check(params.account.allDigits(), params.weightRows.get().get(0).weights);

        assertThat(result, is(equalTo(true)));
    }
}
