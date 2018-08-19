

import com.github.pauldambra.moduluschecker.ModulusChecker
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.io.IOException

class VocalinkTestCases {

    class ExceptionOne {
        @Test
        @Throws(IOException::class)
        fun ensuresThatTwentySevenHasBeenAddedToTheAccumulatedTotal() {
            assertVocalinkTestCase("118765", "64371389", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereItFailsTheDoubleAlternateCheck() {
            assertVocalinkTestCase("118765", "64371388", false)
        }
    }

    class ExceptionTwoAndNine {
        @Test
        @Throws(IOException::class)
        fun whereFirstPassesAndSecondFails() {

            assertVocalinkTestCase("309070", "02355688", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereFirstFailsAndSecondPassesWithSubstitution() {
            assertVocalinkTestCase("309070", "12345668", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereSecondPassesWithNoMatchWeights() {
            assertVocalinkTestCase("309070", "12345677", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereSecondPassesUsingOneMatchWeight() {
            assertVocalinkTestCase("309070", "99345694", true)
        }
    }

    class ExceptionThree {
        @Test
        @Throws(IOException::class)
        fun cEqualsSixSoIgnoreSecondCheck() {
            assertVocalinkTestCase("820000", "73688637", true)
        }

        @Test
        @Throws(IOException::class)
        fun cEqualsNineSoIgnoreSecondCheck() {
            assertVocalinkTestCase("827999", "73988638", true)
        }

        @Test
        @Throws(IOException::class)
        fun cIsNeitherSixNorNineSoRunSecondCheck() {
            assertVocalinkTestCase("827101", "28748352", true)
        }
    }

    class ExceptionFour {
        @Test
        @Throws(IOException::class)
        fun whereTheRemainderIsEqualToTheCheckDigit() {
            assertVocalinkTestCase("134020", "63849203", true)
        }
    }

    class ExceptionFive {
        @Test
        @Throws(IOException::class)
        fun whereTheCheckPasses() {
            assertVocalinkTestCase("938611", "07806039", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereTheCheckPassesWithSubstitution() {
            assertVocalinkTestCase("938600", "42368003", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereBothChecksProduceARemainderOfZeroAndPass() {
            assertVocalinkTestCase("938063", "55065200", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereTheFirstCheckDigitIsCorrectAndTheSecondIsIncorrect() {
            assertVocalinkTestCase("938063", "15764273", false)
        }

        @Test
        @Throws(IOException::class)
        fun whereTheFirstCheckDigitIsIncorrectAndTheSecondCorrect() {
            assertVocalinkTestCase("938063", "15764264", false)
        }

        @Test
        @Throws(IOException::class)
        fun whereTheFirstCheckDigitIsIncorrectWithARemainderOfOne() {
            assertVocalinkTestCase("938063", "15763217", false)
        }
    }

    class ExceptionSix {
        @Test
        @Throws(IOException::class)
        fun whereTheAccountFailsStandardCheckButIsAForeignCurrencyAccount() {
            assertVocalinkTestCase("200915", "41011166", true)
        }
    }

    class ExceptionSeven {
        @Test
        @Throws(IOException::class)
        fun wherePassesButWouldFailTheStandardCheck() {
            assertVocalinkTestCase("772798", "99345694", true)
        }
    }

    class ExceptionEight {
        @Test
        @Throws(IOException::class)
        fun whereTheCheckPasses() {
            assertVocalinkTestCase("086090", "06774744", true)
        }
    }

    class ExceptionTenAndEleven {
        @Test
        @Throws(IOException::class)
        fun whereAccountNumberABEqualsZeroNineAndGEquals9FirstCheckPassesAndSecondFails() {
            assertVocalinkTestCase("871427", "09123496", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereAccountNumberABEqualsNineNineAndGEquals9FirstCheckPassesAndSecondFailsE() {
            assertVocalinkTestCase("871427", "99123496", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereFirstCheckPassesAndSecondFails() {
            assertVocalinkTestCase("871427", "46238510", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereFirstCheckFailsAndSecondPasses() {
            assertVocalinkTestCase("872427", "46238510", true)
        }
    }

    class ExceptionTwelveAndThirteen {
        @Test
        @Throws(IOException::class)
        fun wherePassesModulusElevenButWouldFailModulusTen() {
            assertVocalinkTestCase("074456", "12345112", true)
        }

        @Test
        @Throws(IOException::class)
        fun wherePassesModulusElevenButWouldPassModulusTen() {
            assertVocalinkTestCase("070116", "34012583", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereFailsModulusElevenButPassesModulusTen() {
            assertVocalinkTestCase("074456", "11104102", true)
        }
    }

    class ExceptionFourteen {
        /**Listed in the pdf as example one on page 19 of v 4.30  */
        @Test
        @Throws(IOException::class)
        fun whereTheFirstCheckPasses() {
            assertVocalinkTestCase("180002", "98093517", true)
        }

        @Test
        @Throws(IOException::class)
        fun whereTheFirstCheckFailsAndTheSecondPasses() {
            assertVocalinkTestCase("180002", "00000190", true)
        }
    }

    @Test
    @Throws(IOException::class)
    fun passModulusTenCheck() {
        assertVocalinkTestCase("089999", "66374958", true)
    }

    @Test
    @Throws(IOException::class)
    fun passModulusElevenCheck() {
        assertVocalinkTestCase("107999", "88837491", true)
    }

    @Test
    @Throws(IOException::class)
    fun passModulusElevenAndDblAlChecks() {
        assertVocalinkTestCase("202959", "63748472", true)
    }

    @Test
    @Throws(IOException::class)
    fun passModulusElevenAndFailDblAlChecks() {
        assertVocalinkTestCase("203099", "66831036", false)
    }

    @Test
    @Throws(IOException::class)
    fun failModulusElevenAndPassDblAlChecks() {
        assertVocalinkTestCase("203099", "58716970", false)
    }

    @Test
    @Throws(IOException::class)
    fun failModulusTenCheck() {
        assertVocalinkTestCase("089999", "66374959", false)
    }

    @Test
    @Throws(IOException::class)
    fun failModulusElevenCheck() {
        assertVocalinkTestCase("107999", "88837493", false)
    }

    companion object {
        @Throws(IOException::class)
        private fun modulusChecker(): ModulusChecker {
            return ModulusChecker()
        }

        @Throws(IOException::class)
        private fun assertVocalinkTestCase(sortCode: String, accountNumber: String, expectedValid: Boolean) {
            val actual = modulusChecker().checkBankAccount(sortCode, accountNumber)
            assert.that(actual, equalTo(expectedValid))
        }
    }
}