import com.github.pauldambra.moduluschecker.valacdosFile.SortCodeRange;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SortCodeRangeTests {
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
