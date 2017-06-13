import com.dambra.paul.moduluschecker.*;
import com.dambra.paul.moduluschecker.checks.DoubleAlternate;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

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
                Stream.of(2,1,2,1,2,1,2,1,2,1,2,1,2,1),
                Optional.empty()
                );

        ModulusCheckParams params = new ModulusCheckParams(
                new BankAccount(sc, an),
                Optional.of(row),
                Optional.empty());
        DoubleAlternate checker = new DoubleAlternate();

        Boolean result = checker.check(params.account.allDigits(), params.firstWeightRow.get().weights);

        assertThat(result, is(equalTo(true)));
    }
}
