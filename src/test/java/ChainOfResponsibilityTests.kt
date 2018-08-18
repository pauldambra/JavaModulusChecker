

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test

class ChainOfResponsibilityTests {

    private var firstWasCalled = false
    private var secondWasCalled = false
    private var thirdWasCalled = false

    private inner class First(private val next: ModulusChainLink) : ModulusChainLink {

        override fun check(params: ModulusCheckParams): ModulusResult {
            firstWasCalled = true
            return next.check(params)
        }
    }

    private inner class SecondIsAlwaysResponsible(private val next: ModulusChainLink) : ModulusChainLink {

        override fun check(params: ModulusCheckParams): ModulusResult {
            secondWasCalled = true
            return ModulusResult(false, null)
        }
    }

    private inner class Third : ModulusChainLink {

        override fun check(params: ModulusCheckParams): ModulusResult {
            thirdWasCalled = true
            return ModulusResult(true, null)
        }
    }

    @Test
    fun CanChainTogether() {
        val chain = First(SecondIsAlwaysResponsible(Third()))

        val result = chain.check(ModulusCheckParams(BankAccount("a", "b")))

        assertThat(result.firstCheckResult, `is`(equalTo(false)))
        assertThat(firstWasCalled, `is`(equalTo(true)))
        assertThat(secondWasCalled, `is`(equalTo(true)))
        assertThat(thirdWasCalled, `is`(equalTo(false)))
    }
}
