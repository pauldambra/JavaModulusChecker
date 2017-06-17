import com.dambra.paul.moduluschecker.valacdosFile.SortCodeRange;
import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SortCodeRangeTests {
    @Test
    public void CanMakeSingleItemRange() {
        SortCodeRange sortCodeRange = new SortCodeRange("010023", "010023");
        Assert.thatStreamEquals(sortCodeRange.fullRange(), Stream.of("010023"));
    }

    @Test
    public void SortCodeRangeCanExpandTheRange() {
        SortCodeRange scr = new SortCodeRange("010004", "010010");
        Stream<String> expectedRange = Stream.of("010004", "010005", "010006", "010007", "010008", "010009", "010010");
        Assert.thatStreamEquals(expectedRange, scr.fullRange());
    }

    @Test
    public void SortCodeRangeCanBeAfter() {
        SortCodeRange range = new SortCodeRange("010004", "010010");
        assertThat(range.isAfter("010003"), is(equalTo(true)));
        assertThat(range.isAfter("010004"), is(equalTo(false)));
        assertThat(range.isAfter("010005"), is(equalTo(false)));
    }

    @Test
    public void SortCodeRangeCanBeBefore() {
        SortCodeRange range = new SortCodeRange("010004", "010010");
        assertThat(range.isBefore("010009"), is(equalTo(false)));
        assertThat(range.isBefore("010010"), is(equalTo(false)));
        assertThat(range.isBefore("010011"), is(equalTo(true)));
    }

    @Test
    public void SortCodeRangeCanContain() {
        SortCodeRange range = new SortCodeRange("010004", "010006");
        assertThat(range.contains("010003"), is(equalTo(false)));
        assertThat(range.contains("010004"), is(equalTo(true)));
        assertThat(range.contains("010005"), is(equalTo(true)));
        assertThat(range.contains("010006"), is(equalTo(true)));
        assertThat(range.contains("010007"), is(equalTo(false)));
    }
}
