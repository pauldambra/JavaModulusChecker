import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;


@RunWith(Theories.class)
public class VocalinkTestCases {

    static class VocalinkTestCase {
        public final String sortCode;
        public final String accountNumber;
        public final Boolean expectedValid;

        public VocalinkTestCase(String sortCode, String accountNumber, Boolean expectedValid) {

            this.sortCode = sortCode;
            this.accountNumber = accountNumber;
            this.expectedValid = expectedValid;
        }
    }

    @DataPoint
    public static VocalinkTestCase PASS_MODULUS_10_check = new VocalinkTestCase("089999", "66374958", true);
    @DataPoint
    public static VocalinkTestCase PASS_MODULUS_11_check = new VocalinkTestCase("107999", "88837491", true);
    @DataPoint
    public static VocalinkTestCase PASS_MODULUS_11_AND_double_alternate_checks = new VocalinkTestCase("202959", "63748472", true);
    @DataPoint
    public static VocalinkTestCase Exception_10_and_11_WHERE_first_check_PASSES_AND_second_FAILS = new VocalinkTestCase("871427", "46238510", true);
    @DataPoint
    public static VocalinkTestCase Exception_10_and_11_WHERE_first_check_FAILS_AND_SECOND_PASSES = new VocalinkTestCase("872427", "46238510", true);
    @DataPoint
    public static VocalinkTestCase Exception_10_WHERE_acc_no_ab_EQUALS_09_AND_g_EQUALS_9_first_check_PASSES_AND_second_FAILS = new VocalinkTestCase("871427", "09123496", true);
    @DataPoint
    public static VocalinkTestCase Exception_10_WHERE_acc_no_ab_EQUALS_99_AND_g_EQUALS_9_first_check_PASSES_AND_second_FAILS = new VocalinkTestCase("871427", "99123496", true);
    @DataPoint
    public static VocalinkTestCase Exception_3_AND_c_EQUALS_6_SO_IGNORE_second_check = new VocalinkTestCase("820000", "73688637", true);
    @DataPoint
    public static VocalinkTestCase Exception_3_AND_c_EQUALS_9_SO_IGNORE_second_check = new VocalinkTestCase("827999", "73988638", true);
    @DataPoint
    public static VocalinkTestCase Exception_3_AND_c_IS_NEITHER_6_NOR_9_so_RUN_second_check = new VocalinkTestCase("827101", "28748352", true);
    @DataPoint
    public static VocalinkTestCase Exception_4_WHERE_THE_REMAINDER_IS_equal_TO_the_checkdigit = new VocalinkTestCase("134020", "63849203", true);
    @DataPoint
    public static VocalinkTestCase Exception_1_ensures_that_27_has_been_added_TO_the_ACCUMULATED_TOTAL = new VocalinkTestCase("118765", "64371389", true);
    @DataPoint
    public static VocalinkTestCase Exception_6_WHERE_the_account_FAILS_standard_check_but_is_a_foreign_currency_account = new VocalinkTestCase("200915", "41011166", true);
    @DataPoint
    public static VocalinkTestCase Exception_5_WHERE_the_check_PASSES = new VocalinkTestCase("938611", "07806039", true);
    @DataPoint
    public static VocalinkTestCase Exception_5_WHERE_the_check_PASSES_WITH_SUBSTITUTION = new VocalinkTestCase("938600", "42368003", true);
    @DataPoint
    public static VocalinkTestCase Exception_5_WHERE_both_checks_produce_a_remainder_of_0_AND_pass = new VocalinkTestCase("938063", "55065200", true);
    @DataPoint
    public static VocalinkTestCase Exception_7_WHERE_passes_BUT_WOULD_FAIL_THE_standard_check = new VocalinkTestCase("772798", "99345694", true);
    @DataPoint
    public static VocalinkTestCase Exception_8_WHERE_the_check_PASSES = new VocalinkTestCase("086090", "06774744", true);
    @DataPoint
    public static VocalinkTestCase TWO_AND_9_WHERE_first_PASSES_AND_second_FAILS = new VocalinkTestCase("309070", "02355688", true);
    @DataPoint
    public static VocalinkTestCase TWO_AND_9_WHERE_first_FAILS_AND_SECOND_PASSES_WITH_SUBSTITUTION = new VocalinkTestCase("309070", "12345668", true);
    @DataPoint
    public static VocalinkTestCase TWO_AND_9_SECOND_PASSES_WITH_no_MATCH_WEIGHTS = new VocalinkTestCase("309070", "12345677", true);
    @DataPoint
    public static VocalinkTestCase TWO_AND_9_WHERE_SECOND_PASSES_USING_one_MATCH_WEIGHTS = new VocalinkTestCase("309070", "99345694", true);
    @DataPoint
    public static VocalinkTestCase Exception_5_WHERE_THE_FIRST_checkdigit_IS_correct_AND_the_SECOND_incorrect = new VocalinkTestCase("938063", "15764273", false);
    @DataPoint
    public static VocalinkTestCase Exception_5_WHERE_THE_FIRST_checkdigit_IS_incorrect_AND_the_SECOND_correct = new VocalinkTestCase("938063", "15764264", false);
    @DataPoint
    public static VocalinkTestCase Exception_5_WHERE_THE_FIRST_checkdigit_IS_incorrect_WITH_a_REMAINDER_of_1 = new VocalinkTestCase("938063", "15763217", false);
    @DataPoint
    public static VocalinkTestCase Exception_1_WHERE_it_FAILS_DOUBLE_alternate_check = new VocalinkTestCase("118765", "64371388", false);
    @DataPoint
    public static VocalinkTestCase PASS_MODULUS_11_check_AND_fail_DOUBLE_alternate_check = new VocalinkTestCase("203099", "66831036", false);
    @DataPoint
    public static VocalinkTestCase FAIL_modulus_11_check_AND_pass_DOUBLE_alternate_check = new VocalinkTestCase("203099", "58716970", false);
    @DataPoint
    public static VocalinkTestCase FAIL_modulus_10_check = new VocalinkTestCase("089999", "66374959", false);
    @DataPoint
    public static VocalinkTestCase FAIL_modulus_11_check = new VocalinkTestCase("107999", "88837493", false);
    @DataPoint
    public static VocalinkTestCase Exception_12_AND_13_WHERE_passes_MODULUS_11_BUT_WOULD_FAIL_modulus_10 = new VocalinkTestCase("074456", "12345112", true);
    @DataPoint
    public static VocalinkTestCase Exception_12_AND_13_WHERE_passes_MODULUS_11_BUT_would_PASS_MODULUS_10 = new VocalinkTestCase("070116", "34012583", true);
    @DataPoint
    public static VocalinkTestCase Exception_12_AND_13_WHERE_fails_MODULUS_11_BUT_passes_MODULUS_10 = new VocalinkTestCase("074456", "11104102", true);
    @DataPoint
    public static VocalinkTestCase Exception_14_WHERE_THE_FIRST_check_FAILS_AND_the_second_check_PASSES = new VocalinkTestCase("180002", "00000190", true);

    @Theory
    public void canPassCurrentVocalinkTestCases(VocalinkTestCase testCase)
    {
        ModulusChecker modulusChecker = new ModulusChecker();
        Boolean actual = modulusChecker.checkBankAccount(testCase.sortCode, testCase.accountNumber);
        assertThat(actual, is(equalTo(testCase.expectedValid)));
    }
}