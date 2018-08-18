

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import com.google.common.collect.ImmutableList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class ModulusTenTests {
    @Test
    fun CanRunModulusTenCheck() {
        val sc = "089999"
        val an = "66374958"
        val row = WeightRow(
          ModulusAlgorithm.MOD10,
          ImmutableList.of(0, 0, 0, 0, 0, 0, 7, 1, 3, 7, 1, 3, 7, 1),
          null
        )

        val params = ModulusCheckParams(
          BankAccount(sc, an), row)
        val checker = ModulusTenCheck()

        val result = checker.check(({ obj: ModulusCheckParams -> obj.firstWeightRow!! })(params), params.account)

        assertThat(result, `is`(equalTo(true)))
    }
}
