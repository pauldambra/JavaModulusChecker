import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.dambra.paul.moduluschecker.ModulusChecker;
import org.junit.Test;

import java.io.IOException;

public class VocalinkTestCases {
    private static ModulusChecker modulusChecker() throws IOException {return new ModulusChecker(); }

    public static class ExceptionOne {
        @Test
        public void EnsuresThatTwentySevenHasBeenAddedToTheAccumulatedTotal() throws IOException {
            assertVocalinkTestCase("118765", "64371389", true);
        }

        @Test
        public void WhereItFailsTheDoubleAlternateCheck() throws IOException {
            assertVocalinkTestCase("118765", "64371388", false);
        }
    }

    public static class ExceptionTwoAndNine {
        @Test
        public void WhereFirstPassesAndSecondFails() throws IOException {

            assertVocalinkTestCase("309070", "02355688", true);
        }

        @Test
        public void WhereFirstFailsAndSecondPassesWithSubstitution() throws IOException {
            assertVocalinkTestCase("309070", "12345668", true);
        }

        @Test
        public void WhereSecondPassesWithNoMatchWeights() throws IOException {
            assertVocalinkTestCase("309070", "12345677", true);
        }
        @Test
        public void WhereSecondPassesUsingOneMatchWeight() throws IOException {
            assertVocalinkTestCase("309070", "99345694", true);
        }
    }

    public static class ExceptionThree {
        @Test
        public void CEqualsSixSoIgnoreSecondCheck() throws IOException {
            assertVocalinkTestCase("820000", "73688637", true);
        }
        @Test
        public void CEqualsNineSoIgnoreSecondCheck() throws IOException {
            assertVocalinkTestCase("827999", "73988638", true);
        }
        @Test
        public void CIsNeitherSixNorNineSoRunSecondCheck() throws IOException {
            assertVocalinkTestCase("827101", "28748352", true);
        }
    }

    public static class ExceptionFour {
        @Test
        public void WhereTheRemainderIsEqualToTheCheckDigit() throws IOException {
            assertVocalinkTestCase("134020", "63849203", true);
        }
    }

    public static class ExceptionFive {
        @Test
        public void WhereTheCheckPasses() throws IOException {
            assertVocalinkTestCase("938611", "07806039", true);
        }

        @Test
        public void WhereTheCheckPassesWithSubstitution() throws IOException {
            assertVocalinkTestCase("938600", "42368003", true);
        }

        @Test
        public void WhereBothChecksProduceARemainderOfZeroAndPass() throws IOException {
            assertVocalinkTestCase("938063", "55065200", true);
        }

        @Test
        public void WhereTheFirstCheckDigitIsCorrectAndTheSecondIsIncorrect() throws IOException {
            assertVocalinkTestCase("938063", "15764273", false);
        }

        @Test
        public void WhereTheFirstCheckDigitIsIncorrectAndTheSecondCorrect() throws IOException {
            assertVocalinkTestCase("938063", "15764264", false);
        }

        @Test
        public void WhereTheFirstCheckDigitIsIncorrectWithARemainderOfOne() throws IOException {
            assertVocalinkTestCase("938063", "15763217", false);
        }
    }

    @Test
    public void PASS_MODULUS_10_check() throws IOException {
        assertVocalinkTestCase("089999", "66374958", true);
    }
    @Test
    public void PASS_MODULUS_11_check() throws IOException {
        assertVocalinkTestCase("107999", "88837491", true);
    }
    @Test
    public void PASS_MODULUS_11_AND_double_alternate_checks() throws IOException {
        assertVocalinkTestCase("202959", "63748472", true);
    }
    @Test
    public void Exception_10_and_11_WHERE_first_check_PASSES_AND_second_FAILS() throws IOException {
        assertVocalinkTestCase("871427", "46238510", true);
    }
    @Test
    public void Exception_10_and_11_WHERE_first_check_FAILS_AND_SECOND_PASSES() throws IOException {
        assertVocalinkTestCase("872427", "46238510", true);
    }
    @Test
    public void Exception_10_WHERE_acc_no_ab_EQUALS_09_AND_g_EQUALS_9_first_check_PASSES_AND_second_FAILS() throws IOException {
        assertVocalinkTestCase("871427", "09123496", true);
    }
    @Test
    public void Exception_10_WHERE_acc_no_ab_EQUALS_99_AND_g_EQUALS_9_first_check_PASSES_AND_second_FAILS() throws IOException {
        assertVocalinkTestCase("871427", "99123496", true);
    }

    @Test
    public void Exception_6_WHERE_the_account_FAILS_standard_check_but_is_a_foreign_currency_account() throws IOException {
        assertVocalinkTestCase("200915", "41011166", true);
    }

    @Test
    public void Exception_7_WHERE_passes_BUT_WOULD_FAIL_THE_standard_check() throws IOException {
        assertVocalinkTestCase("772798", "99345694", true);
    }

    @Test
    public void Exception_8_WHERE_the_check_PASSES() throws IOException {
        assertVocalinkTestCase("086090", "06774744", true);
    }

    @Test
    public void PASS_MODULUS_11_check_AND_fail_DOUBLE_alternate_check() throws IOException {
        assertVocalinkTestCase("203099", "66831036", false);
    }
    @Test
    public void FAIL_modulus_11_check_AND_pass_DOUBLE_alternate_check() throws IOException {
        assertVocalinkTestCase("203099", "58716970", false);
    }
    @Test
    public void FAIL_modulus_10_check() throws IOException {
        assertVocalinkTestCase("089999", "66374959", false);
    }
    @Test
    public void FAIL_modulus_11_check() throws IOException {
        assertVocalinkTestCase("107999", "88837493", false);
    }
    @Test
    public void Exception_12_AND_13_WHERE_passes_MODULUS_11_BUT_WOULD_FAIL_modulus_10() throws IOException {
        assertVocalinkTestCase("074456", "12345112", true);
    }
    @Test
    public void Exception_12_AND_13_WHERE_passes_MODULUS_11_BUT_would_PASS_MODULUS_10() throws IOException {
        assertVocalinkTestCase("070116", "34012583", true);
    }
    @Test
    public void Exception_12_AND_13_WHERE_fails_MODULUS_11_BUT_passes_MODULUS_10() throws IOException {
        assertVocalinkTestCase("074456", "11104102", true);
    }
    @Test
    public void Exception_14_WHERE_THE_FIRST_check_FAILS_AND_the_second_check_PASSES() throws IOException {
        assertVocalinkTestCase("180002", "00000190", true);
    }

    private static void assertVocalinkTestCase(String sortCode, String accountNumber, Boolean expectedValid) throws IOException {
        Boolean actual = modulusChecker().checkBankAccount(sortCode, accountNumber);
        assertThat(actual, is(equalTo(expectedValid)));
    }
}