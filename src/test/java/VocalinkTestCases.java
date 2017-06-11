import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.dambra.paul.moduluschecker.ModulusChecker;
import org.junit.Test;

public class VocalinkTestCases {

    @Test
    public void PASS_MODULUS_10_check() {
        assertVocalinkTestCase("089999", "66374958", true);
    }
    @Test
    public void PASS_MODULUS_11_check() {
        assertVocalinkTestCase("107999", "88837491", true);
    }
    @Test
    public void PASS_MODULUS_11_AND_double_alternate_checks() {
        assertVocalinkTestCase("202959", "63748472", true);
    }
    @Test
    public void Exception_10_and_11_WHERE_first_check_PASSES_AND_second_FAILS() {
        assertVocalinkTestCase("871427", "46238510", true);
    }
    @Test
    public void Exception_10_and_11_WHERE_first_check_FAILS_AND_SECOND_PASSES() {
        assertVocalinkTestCase("872427", "46238510", true);
    }
    @Test
    public void Exception_10_WHERE_acc_no_ab_EQUALS_09_AND_g_EQUALS_9_first_check_PASSES_AND_second_FAILS() {
        assertVocalinkTestCase("871427", "09123496", true);
    }
    @Test
    public void Exception_10_WHERE_acc_no_ab_EQUALS_99_AND_g_EQUALS_9_first_check_PASSES_AND_second_FAILS() {
        assertVocalinkTestCase("871427", "99123496", true);
    }
    @Test
    public void Exception_3_AND_c_EQUALS_6_SO_IGNORE_second_check() {
        assertVocalinkTestCase("820000", "73688637", true);
    }
    @Test
    public void Exception_3_AND_c_EQUALS_9_SO_IGNORE_second_check() {
        assertVocalinkTestCase("827999", "73988638", true);
    }
    @Test
    public void Exception_3_AND_c_IS_NEITHER_6_NOR_9_so_RUN_second_check() {
        assertVocalinkTestCase("827101", "28748352", true);
    }
    @Test
    public void Exception_4_WHERE_THE_REMAINDER_IS_equal_TO_the_checkdigit() {
        assertVocalinkTestCase("134020", "63849203", true);
    }
    @Test
    public void Exception_1_ensures_that_27_has_been_added_TO_the_ACCUMULATED_TOTAL() {
        assertVocalinkTestCase("118765", "64371389", true);
    }
    @Test
    public void Exception_6_WHERE_the_account_FAILS_standard_check_but_is_a_foreign_currency_account() {
        assertVocalinkTestCase("200915", "41011166", true);
    }
    @Test
    public void Exception_5_WHERE_the_check_PASSES() {
        assertVocalinkTestCase("938611", "07806039", true);
    }
    @Test
    public void Exception_5_WHERE_the_check_PASSES_WITH_SUBSTITUTION() {
        assertVocalinkTestCase("938600", "42368003", true);
    }
    @Test
    public void Exception_5_WHERE_both_checks_produce_a_remainder_of_0_AND_pass() {
        assertVocalinkTestCase("938063", "55065200", true);
    }
    @Test
    public void Exception_7_WHERE_passes_BUT_WOULD_FAIL_THE_standard_check() {
        assertVocalinkTestCase("772798", "99345694", true);
    }
    @Test
    public void Exception_8_WHERE_the_check_PASSES() {
        assertVocalinkTestCase("086090", "06774744", true);
    }
    @Test
    public void TWO_AND_9_WHERE_first_PASSES_AND_second_FAILS() {
        assertVocalinkTestCase("309070", "02355688", true);
    }
    @Test
    public void TWO_AND_9_WHERE_first_FAILS_AND_SECOND_PASSES_WITH_SUBSTITUTION() {
        assertVocalinkTestCase("309070", "12345668", true);
    }
    @Test
    public void TWO_AND_9_SECOND_PASSES_WITH_no_MATCH_WEIGHTS() {
        assertVocalinkTestCase("309070", "12345677", true);
    }
    @Test
    public void TWO_AND_9_WHERE_SECOND_PASSES_USING_one_MATCH_WEIGHTS() {
        assertVocalinkTestCase("309070", "99345694", true);
    }
    @Test
    public void Exception_5_WHERE_THE_FIRST_checkdigit_IS_correct_AND_the_SECOND_incorrect() {
        assertVocalinkTestCase("938063", "15764273", false);
    }
    @Test
    public void Exception_5_WHERE_THE_FIRST_checkdigit_IS_incorrect_AND_the_SECOND_correct() {
        assertVocalinkTestCase("938063", "15764264", false);
    }
    @Test
    public void Exception_5_WHERE_THE_FIRST_checkdigit_IS_incorrect_WITH_a_REMAINDER_of_1() {
        assertVocalinkTestCase("938063", "15763217", false);
    }
    @Test
    public void Exception_1_WHERE_it_FAILS_DOUBLE_alternate_check() {
        assertVocalinkTestCase("118765", "64371388", false);
    }
    @Test
    public void PASS_MODULUS_11_check_AND_fail_DOUBLE_alternate_check() {
        assertVocalinkTestCase("203099", "66831036", false);
    }
    @Test
    public void FAIL_modulus_11_check_AND_pass_DOUBLE_alternate_check() {
        assertVocalinkTestCase("203099", "58716970", false);
    }
    @Test
    public void FAIL_modulus_10_check() {
        assertVocalinkTestCase("089999", "66374959", false);
    }
    @Test
    public void FAIL_modulus_11_check() {
        assertVocalinkTestCase("107999", "88837493", false);
    }
    @Test
    public void Exception_12_AND_13_WHERE_passes_MODULUS_11_BUT_WOULD_FAIL_modulus_10() {
        assertVocalinkTestCase("074456", "12345112", true);
    }
    @Test
    public void Exception_12_AND_13_WHERE_passes_MODULUS_11_BUT_would_PASS_MODULUS_10() {
        assertVocalinkTestCase("070116", "34012583", true);
    }
    @Test
    public void Exception_12_AND_13_WHERE_fails_MODULUS_11_BUT_passes_MODULUS_10() {
        assertVocalinkTestCase("074456", "11104102", true);
    }
    @Test
    public void Exception_14_WHERE_THE_FIRST_check_FAILS_AND_the_second_check_PASSES() {
        assertVocalinkTestCase("180002", "00000190", true);
    }

    private void assertVocalinkTestCase(String sortCode, String accountNumber, Boolean expectedValid) {
        ModulusChecker modulusChecker = new ModulusChecker();
        Boolean actual = modulusChecker.checkBankAccount(sortCode, accountNumber);
        assertThat(actual, is(equalTo(expectedValid)));
    }
}