import com.dambra.paul.moduluschecker.*;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoadModulusWeightRowTests {

    @Test
    public void SortCodeRangeCanExpandTheRange() {
        SortCodeRange scr = new SortCodeRange("010004", "010010");
        Stream<String> expectedRange = Stream.of("010004", "010005", "010006", "010007", "010008", "010009", "010010");
        Assert.thatStreamEquals(expectedRange, scr.fullRange);
    }

    @Test
    public void CanParseAModulusWeightRow() throws UnknownAlgorithmException {
        String input = "074456 074456 MOD10    0    3    2    4    5    8    9    4    5    6    7    8    9   -1  13";

        WeightRow row = WeightRow.parse(input);

        assertThat(row.modulusAlgorithm, is(equalTo(ModulusAlgorithm.MOD10)));
        assertThat(row.exception, is(equalTo(Optional.of(13))));
        Assert.thatStreamEquals(row.weights, Stream.of(0, 3, 2, 4, 5, 8, 9, 4, 5, 6, 7, 8, 9, -1));
    }

    @Test
    public void CanLoadModulusWeightRow() {
        WeightRow weightRow = new WeightRow(ModulusAlgorithm.MOD10, Stream.empty(), null);
        SortCodeRange sortCodeRange = new SortCodeRange("012344", "012346");

        ImmutableMap<SortCodeRange,WeightRow> weights = ImmutableMap.<SortCodeRange, WeightRow>builder()
                .put(sortCodeRange, weightRow)
                .build();
        ModulusWeightRows modulusRows = new ModulusWeightRows(weights);

        BankAccount originalAccount = new BankAccount("012345", "01234567");
        ModulusCheckParams modulusCheckParams = modulusRows.FindFor(originalAccount);

        assertThat(modulusCheckParams.account, is(equalTo(originalAccount)));
        //noinspection ConstantConditions
        assertThat(modulusCheckParams.weightRow.get(), is(equalTo(weightRow)));
    }

    @Test
    public void ReturnsUnmatchedRowWhenNoSortCodeRangeMatches() {
        WeightRow weightRow = new WeightRow(ModulusAlgorithm.MOD10, Stream.empty(), null);
        SortCodeRange sortCodeRange = new SortCodeRange("012346", "012347");

        ImmutableMap<SortCodeRange,WeightRow> weights = ImmutableMap.<SortCodeRange, WeightRow>builder()
                .put(sortCodeRange, weightRow)
                .build();
        ModulusWeightRows modulusRows = new ModulusWeightRows(weights);

        BankAccount originalAccount = new BankAccount("012345", "01234567");
        ModulusCheckParams modulusCheckParams = modulusRows.FindFor(originalAccount);

        assertThat(modulusCheckParams.weightRow.isPresent(), is(equalTo(false)));
    }
}
