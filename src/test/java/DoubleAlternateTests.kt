

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import com.google.common.collect.ImmutableList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class DoubleAlternateTests {

    @Test
    fun CanRunDoubleAlternateCheck() {
        val sc = "499273"
        val an = "12345678"
        val row = WeightRow(
          ModulusAlgorithm.DOUBLE_ALTERNATE,
          ImmutableList.of(2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1),
          null
        )

        val params = ModulusCheckParams(BankAccount(sc, an), row)
        val checker = DoubleAlternateCheck()

        val result = checker.check(({ obj: ModulusCheckParams -> obj.firstWeightRow!! })(params), params.account)

        assertThat(result, `is`(equalTo(true)))
    }
}
