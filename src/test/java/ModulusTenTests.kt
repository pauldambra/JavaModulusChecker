

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.checks.ModulusTenCheck
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ModulusTenTests {
    @Test
    fun canRunModulusTenCheck() {
        val sc = "089999"
        val an = "66374958"
        val row = WeightRow(
          ModulusAlgorithm.MOD10,
          listOf(0, 0, 0, 0, 0, 0, 7, 1, 3, 7, 1, 3, 7, 1),
          null
        )

        val params = ModulusCheckParams(
          BankAccount(sc, an), row)
        val checker = ModulusTenCheck()

        val result = checker.check(params.account, params.firstWeightRow!!)

        assert.that(result, equalTo(true))
    }
}
