

import com.github.pauldambra.moduluschecker.chain.ModulusResult
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ModulusResultTests {
    @Test
    fun canCopyFirstResult() {
        val original = ModulusResult(true, null)

        val copy = ModulusResult.copy(original)

        assertThat(copy?.firstException, `is`(null as Int?))
        assertThat(copy?.secondException, `is`(null as Int?))

        assertThat(copy?.firstCheckResult ?: false, `is`(true))
        assertThat(copy?.secondCheckResult, `is`(null as Boolean?))
    }

    @Test
    fun canCopySecondResult() {
        val original = ModulusResult(true, false)

        val copy = ModulusResult.copy(original)

        assertThat(copy?.firstException, `is`(null as Int?))
        assertThat(copy?.secondException, `is`(null as Int?))

        assertThat(copy?.firstCheckResult ?: false, `is`(true))
        assertThat(copy?.secondCheckResult ?: true, `is`(false))
    }

    @Test
    fun canCopyFirstException() {
        val original = ModulusResult(true, false)
        val modulusResult = original.withFirstException(5)
        val copy = ModulusResult.copy(modulusResult)

        assertThat(copy!!.firstException, `is`(5))
        assertThat(copy.secondException, `is`(null as Int?))

        assertThat(copy.firstCheckResult ?: false, `is`(true))
        assertThat(copy.secondCheckResult ?: true, `is`(false))
    }

    @Test
    fun canCopySecondException() {
        val original = ModulusResult(true, false)
        val modulusResult = original.withSecondException(10)
        val copy = ModulusResult.copy(modulusResult)

        assertThat(copy?.firstException, `is`(null as Int?))
        assertThat(copy!!.secondException, `is`(10))

        assertThat(copy.firstCheckResult ?: false, `is`(true))
        assertThat(copy.secondCheckResult ?: true, `is`(false))
    }

    @Test
    fun canCopyBothExceptions() {
        val original = ModulusResult(true, false)
        val x = original.withFirstException(3)
        val y = x.withSecondException(4)
        val copy = ModulusResult.copy(y)

        assertThat(copy!!.firstException, `is`(3))
        assertThat(copy.secondException, `is`(4))

        assertThat(copy.firstCheckResult ?: false, `is`(true))
        assertThat(copy.secondCheckResult ?: true, `is`(false))
    }

    @Test
    fun canCopyWithSecondResult() {
        val original = ModulusResult(true, null)
        val x = original.withFirstException(6)
        val y = x.withSecondException(7)
        val copy = ModulusResult.withSecondResult(y, true)

        assertThat(copy.firstException, `is`(6))
        assertThat(copy.secondException, `is`(7))

        assertThat(copy.firstCheckResult ?: false, `is`(true))
        assertThat(copy.secondCheckResult ?: false, `is`(true))
    }
}
