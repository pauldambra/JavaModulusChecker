import com.dambra.paul.moduluschecker.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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

        WeightRow row = WeightRow.parse(input).get();

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
        assertThat(modulusCheckParams.firstWeightRow.get(), is(equalTo(weightRow)));
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

        assertThat(modulusCheckParams.firstWeightRow.isPresent(), is(equalTo(false)));
    }

    @Test
    public void CanLoadFromFileResource() {
        Optional<ModulusWeightRows> modulusWeightRows = ModulusWeightRows.fromFile("file/valacdos.txt");
        assertThat(modulusWeightRows.isPresent(), is(equalTo(true)));

        BankAccount ba = new BankAccount("938173", "01234567");
        ModulusWeightRows weightRows = modulusWeightRows.get();
        ModulusCheckParams found = weightRows.FindFor(ba);

        List<ModulusAlgorithm> modulusAlgorithms = ImmutableList.of(
                found.firstWeightRow.get().modulusAlgorithm,
                found.secondWeightRow.get().modulusAlgorithm
        );
        assertThat(modulusAlgorithms, containsInAnyOrder(ModulusAlgorithm.MOD11, ModulusAlgorithm.DOUBLE_ALTERNATE));
    }
}
