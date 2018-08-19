

import com.github.pauldambra.moduluschecker.SortCodeSubstitution
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.sameInstance
import org.junit.Test
import java.io.IOException

class SortCodeSubstitutionTests {
    @Test
    fun sortCodesNotInTheTableAreUnchanged() {

        val substitutions = mapOf("not_the_sort_code" to "12345")

        val sortCodeSubstitution = SortCodeSubstitution(substitutions)
        val originalAccount = BankAccount("012345", "01234567")
        val account = sortCodeSubstitution.apply(originalAccount)

        assert.that(account.sortCode, equalTo("012345"))
        assert.that(account.accountNumber, equalTo("01234567"))
        assert.that(account, !sameInstance(originalAccount))
    }

    @Test
    fun sortCodesInTheTableAreChanged() {

        val substitutions = mapOf("012345" to "543210")

        val sortCodeSubstitution = SortCodeSubstitution(substitutions)
        val originalAccount = BankAccount("012345", "01234567")
        val account = sortCodeSubstitution.apply(originalAccount)

        assert.that(account.sortCode, equalTo("543210"))
        assert.that(account.accountNumber, equalTo("01234567"))
        assert.that(account, !sameInstance(originalAccount))
    }

    @Test
    @Throws(IOException::class)
    fun canLoadFromFileResource() {
        val sortCodeSubstitution = SortCodeSubstitution.fromFile("file/scsubtab.txt")

        val ba = BankAccount("938173", "01234567")
        assert.that(sortCodeSubstitution.apply(ba).sortCode, equalTo("938017"))
    }
}
