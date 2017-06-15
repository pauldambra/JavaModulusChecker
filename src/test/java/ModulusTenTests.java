import com.dambra.paul.moduluschecker.*;
import com.dambra.paul.moduluschecker.chain.ModulusTenCheck;
import com.google.common.collect.Streams;
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
                Optional.of(row), Optional.empty());
        ModulusTenCheck checker = new ModulusTenCheck();


        Boolean result = checker.check(params, x -> x.firstWeightRow.get());

        assertThat(result, is(equalTo(true)));
    }
}
