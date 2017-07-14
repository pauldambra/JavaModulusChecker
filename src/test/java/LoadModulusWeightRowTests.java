import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.ModulusAlgorithm;
import com.github.pauldambra.moduluschecker.ModulusCheckParams;
import com.github.pauldambra.moduluschecker.UnknownAlgorithmException;
import com.github.pauldambra.moduluschecker.valacdosFile.SortCodeRange;
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows;
import com.github.pauldambra.moduluschecker.valacdosFile.ValacdosRow;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;
import com.google.common.collect.ImmutableList;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;

public class LoadModulusWeightRowTests {

    @Test
    public void CanParseAModulusWeightRow() throws UnknownAlgorithmException {
        String input = "074456 074456 MOD10    0    3    2    4    5    8    9    4    5    6    7    8    9   -1  13";

        WeightRow row = WeightRow.parse(input).orElse(null);

        assertThat(row.modulusAlgorithm, is(equalTo(ModulusAlgorithm.MOD10)));
        assertThat(row.exception, is(equalTo(Optional.of(13))));
        assertThat(row.getWeights(), is(equalTo(ImmutableList.of(0, 3, 2, 4, 5, 8, 9, 4, 5, 6, 7, 8, 9, -1))));
    }

    @Test
    public void CanLoadModulusWeightRow() {
        WeightRow weightRow = new WeightRow(ModulusAlgorithm.MOD10, ImmutableList.of(), null);
        SortCodeRange sortCodeRange = new SortCodeRange("012344", "012346");

        ModulusWeightRows modulusRows = new ModulusWeightRows(Arrays.asList(new ValacdosRow(sortCodeRange, weightRow)));

        BankAccount originalAccount = BankAccount.Of("012345", "01234567");
        ModulusCheckParams modulusCheckParams = modulusRows.FindFor(originalAccount);

        assertThat(modulusCheckParams.account, is(equalTo(originalAccount)));
        assertThat(Optional.ofNullable(
                WeightRow.copy(modulusCheckParams.firstWeightRow.orElse(null))).orElse(null), is(equalTo(weightRow)));
    }

    @Test
    public void ReturnsUnmatchedRowWhenNoSortCodeRangeMatches() {
        WeightRow weightRow = new WeightRow(ModulusAlgorithm.MOD10, ImmutableList.of(), null);
        SortCodeRange sortCodeRange = new SortCodeRange("012346", "012347");

        ModulusWeightRows modulusRows = new ModulusWeightRows(Arrays.asList(new ValacdosRow(sortCodeRange, weightRow)));

        BankAccount originalAccount = BankAccount.Of("012345", "01234567");
        ModulusCheckParams modulusCheckParams = modulusRows.FindFor(originalAccount);

        assertThat(Optional.ofNullable(
                WeightRow.copy(modulusCheckParams.firstWeightRow.orElse(null))).isPresent(), is(equalTo(false)));
    }

    @Test
    public void CanLoadFromFileResource() throws IOException {
        ModulusWeightRows weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

        BankAccount ba = BankAccount.Of("938173", "01234567");

        ModulusCheckParams found = weightRows.FindFor(ba);

        List<ModulusAlgorithm> modulusAlgorithms = ImmutableList.of(
                Optional.ofNullable(
                        WeightRow.copy(found.firstWeightRow.orElse(null))).orElse(null).modulusAlgorithm,
                Optional.ofNullable(
                        WeightRow.copy(found.secondWeightRow.orElse(null))).orElse(null).modulusAlgorithm
        );
        assertThat(modulusAlgorithms, containsInAnyOrder(ModulusAlgorithm.MOD11, ModulusAlgorithm.DOUBLE_ALTERNATE));
    }

    /**
     * This was a bug where order of loaded rows was different between debug and run configs o_O
     */
    @Test
    public void CanLoadFromFileResourceAndGetALloydsAccount() throws IOException {
        ModulusWeightRows weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

        BankAccount ba = BankAccount.Of("309070", "12345668");

        ModulusCheckParams found = weightRows.FindFor(ba);

        assertThat(Optional.ofNullable(
                WeightRow.copy(found.firstWeightRow.orElse(null))).get().exception.get(), is(equalTo(2)));
        assertThat(Optional.ofNullable(
                WeightRow.copy(found.secondWeightRow.orElse(null))).get().exception.get(), is(equalTo(9)));
    }
}
