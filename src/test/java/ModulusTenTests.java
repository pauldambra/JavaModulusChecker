import com.dambra.paul.moduluschecker.*;
import com.dambra.paul.moduluschecker.checks.ModulusTen;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ModulusTenTests {
    @Test
    public void CanRunModulusTenCheck() {
        String sc = "089999";
        String an = "66374958";
        WeightRow row = new WeightRow(
                ModulusAlgorithm.MOD10,
                Stream.of(0, 0, 0, 0, 0, 0, 7, 1, 3, 7, 1, 3, 7, 1),
                Optional.empty()
        );

        ModulusCheckParams params = new ModulusCheckParams(
                new BankAccount(sc, an),
                Optional.of(ImmutableList.of(row)));
        ModulusTen checker = new ModulusTen();

        Boolean result = checker.check(params.account.allDigits(), params.weightRows.get().get(0).weights);

        assertThat(result, is(equalTo(true)));
    }
}
