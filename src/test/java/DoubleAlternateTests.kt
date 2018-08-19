

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.checks.DoubleAlternateCheck
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class DoubleAlternateTests {

    @Test
    fun canRunDoubleAlternateCheck() {
        val sc = "499273"
        val an = "12345678"
        val row = WeightRow(
          ModulusAlgorithm.DOUBLE_ALTERNATE,
          listOf(2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1),
          null
        )

        val params = ModulusCheckParams(BankAccount(sc, an), row)
        val checker = DoubleAlternateCheck()

        val result = checker.check(params.account, params.firstWeightRow!!)

        assert.that(result, equalTo(true))
    }
}
