

import com.github.pauldambra.moduluschecker.valacdosFile.SortCodeRange
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SortCodeRangeTests {
    @Test
    fun sortCodeRangeCanContain() {
        val range = SortCodeRange("010004", "010006")
        assert.that(range.contains("010003"), equalTo(false))
        assert.that(range.contains("010004"), equalTo(true))
        assert.that(range.contains("010005"), equalTo(true))
        assert.that(range.contains("010006"), equalTo(true))
        assert.that(range.contains("010007"), equalTo(false))
    }
}
