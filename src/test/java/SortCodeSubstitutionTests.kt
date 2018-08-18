

import com.github.pauldambra.moduluschecker.SortCodeSubstitution
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.google.common.collect.ImmutableMap
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.theInstance
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsNot.not
import org.junit.Test
import java.io.IOException

class SortCodeSubstitutionTests {
    @Test
    fun SortCodesNotInTheTableAreUnchanged() {

        val substitutions = ImmutableMap.builder<String, String>()
          .put("not_the_sort_code", "12345")
          .build()

        val sortCodeSubstitution = SortCodeSubstitution(substitutions)
        val originalAccount = BankAccount("012345", "01234567")
        val account = sortCodeSubstitution.apply(originalAccount)

        assertThat(account.sortCode, `is`(equalTo("012345")))
        assertThat(account.accountNumber, `is`(equalTo("01234567")))
        assertThat(account, `is`(not(theInstance(originalAccount))))
    }

    @Test
    fun SortCodesInTheTableAreChanged() {

        val substitutions = ImmutableMap.builder<String, String>()
          .put("012345", "543210")
          .build()

        val sortCodeSubstitution = SortCodeSubstitution(substitutions)
        val originalAccount = BankAccount("012345", "01234567")
        val account = sortCodeSubstitution.apply(originalAccount)

        assertThat(account.sortCode, `is`(equalTo("543210")))
        assertThat(account.accountNumber, `is`(equalTo("01234567")))
        assertThat(account, `is`(not(theInstance(originalAccount))))
    }

    @Test
    @Throws(IOException::class)
    fun CanLoadFromFileResource() {
        val sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt")

        val ba = BankAccount("938173", "01234567")
        assertThat(sortCodeSubstitution.apply(ba).sortCode, `is`(equalTo("938017")))
    }
}
