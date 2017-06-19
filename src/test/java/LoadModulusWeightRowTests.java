import com.dambra.paul.moduluschecker.*;
import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.valacdosFile.SortCodeRange;
import com.dambra.paul.moduluschecker.valacdosFile.ModulusWeightRows;
import com.dambra.paul.moduluschecker.valacdosFile.ValacdosRow;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;
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

        BankAccount originalAccount = new BankAccount("012345", "01234567");
        ModulusCheckParams modulusCheckParams = modulusRows.FindFor(originalAccount);

        assertThat(modulusCheckParams.getAccount(), is(equalTo(originalAccount)));
        assertThat(modulusCheckParams.getFirstWeightRow().orElse(null), is(equalTo(weightRow)));
    }

    @Test
    public void ReturnsUnmatchedRowWhenNoSortCodeRangeMatches() {
        WeightRow weightRow = new WeightRow(ModulusAlgorithm.MOD10, ImmutableList.of(), null);
        SortCodeRange sortCodeRange = new SortCodeRange("012346", "012347");

        ModulusWeightRows modulusRows = new ModulusWeightRows(Arrays.asList(new ValacdosRow(sortCodeRange, weightRow)));

        BankAccount originalAccount = new BankAccount("012345", "01234567");
        ModulusCheckParams modulusCheckParams = modulusRows.FindFor(originalAccount);

        assertThat(modulusCheckParams.getFirstWeightRow().isPresent(), is(equalTo(false)));
    }

    @Test
    public void CanLoadFromFileResource() throws IOException {
        ModulusWeightRows weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

        BankAccount ba = new BankAccount("938173", "01234567");

        ModulusCheckParams found = weightRows.FindFor(ba);

        List<ModulusAlgorithm> modulusAlgorithms = ImmutableList.of(
                found.getFirstWeightRow().orElse(null).modulusAlgorithm,
                found.getSecondWeightRow().orElse(null).modulusAlgorithm
        );
        assertThat(modulusAlgorithms, containsInAnyOrder(ModulusAlgorithm.MOD11, ModulusAlgorithm.DOUBLE_ALTERNATE));
    }

    /**
     * This was a bug where order of loaded rows was different between debug and run configs o_O
     */
    @Test
    public void CanLoadFromFileResourceAndGetALloydsAccount() throws IOException {
        ModulusWeightRows weightRows = ModulusWeightRows.fromFile("file/valacdos.txt");

        BankAccount ba = new BankAccount("309070", "12345668");

        ModulusCheckParams found = weightRows.FindFor(ba);

        assertThat(found.getFirstWeightRow().get().exception.get(), is(equalTo(2)));
        assertThat(found.getSecondWeightRow().get().exception.get(), is(equalTo(9)));
    }
}
