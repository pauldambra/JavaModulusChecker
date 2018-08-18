

import com.github.pauldambra.moduluschecker.valacdosFile.SortCodeRange
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class SortCodeRangeTests {
    @Test
    fun sortCodeRangeCanContain() {
        val range = SortCodeRange("010004", "010006")
        assertThat(range.contains("010003"), `is`(equalTo(false)))
        assertThat(range.contains("010004"), `is`(equalTo(true)))
        assertThat(range.contains("010005"), `is`(equalTo(true)))
        assertThat(range.contains("010006"), `is`(equalTo(true)))
        assertThat(range.contains("010007"), `is`(equalTo(false)))
    }
}
