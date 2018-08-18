

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.UnknownAlgorithmException
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows
import com.github.pauldambra.moduluschecker.valacdosFile.SortCodeRange
import com.github.pauldambra.moduluschecker.valacdosFile.ValacdosRow
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import com.google.common.collect.ImmutableList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Test
import java.io.IOException
import java.util.*

class LoadModulusWeightRowTests {

    @Test
    @Throws(UnknownAlgorithmException::class)
    fun canParseAModulusWeightRow() {
        val input = "074456 074456 MOD10    0    3    2    4    5    8    9    4    5    6    7    8    9   -1  13"

        val row = WeightRow.parse(input)

        assertThat(row?.modulusAlgorithm, `is`(equalTo(ModulusAlgorithm.MOD10)))
        assertThat(row?.exception, `is`(equalTo(13)))
        assertThat(row?.weights, `is`(equalTo(listOf(0, 3, 2, 4, 5, 8, 9, 4, 5, 6, 7, 8, 9, -1))))
    }

    @Test
    fun canLoadModulusWeightRow() {
        val weightRow = WeightRow(ModulusAlgorithm.MOD10, ImmutableList.of(), null)
        val sortCodeRange = SortCodeRange("012344", "012346")

        val modulusRows = ModulusWeightRows(Arrays.asList(ValacdosRow(sortCodeRange, weightRow)))

        val originalAccount = BankAccount.with("012345", "01234567")
        val modulusCheckParams = modulusRows.FindFor(originalAccount)

        assertThat(modulusCheckParams.account, `is`(equalTo(originalAccount)))
        assertThat(modulusCheckParams.firstWeightRow!!, `is`(equalTo(weightRow)))
    }

    @Test
    fun returnsUnmatchedRowWhenNoSortCodeRangeMatches() {
        val weightRow = WeightRow(ModulusAlgorithm.MOD10, ImmutableList.of(), null)
        val sortCodeRange = SortCodeRange("012346", "012347")

        val modulusRows = ModulusWeightRows(Arrays.asList(ValacdosRow(sortCodeRange, weightRow)))

        val originalAccount = BankAccount.with("012345", "01234567")
        val modulusCheckParams = modulusRows.FindFor(originalAccount)

        assertThat(modulusCheckParams.firstWeightRow, `is`(equalTo(null as WeightRow?)))
    }

    @Test
    @Throws(IOException::class)
    fun canLoadFromFileResource() {
        val weightRows = ModulusWeightRows.fromFile("file/valacdos.txt")

        val ba = BankAccount.with("938173", "01234567")

        val found = weightRows.FindFor(ba)

        val modulusAlgorithms = listOf(
          found.firstWeightRow?.modulusAlgorithm,
          found.secondWeightRow?.modulusAlgorithm
        )
        assertThat(modulusAlgorithms, containsInAnyOrder(ModulusAlgorithm.MOD11, ModulusAlgorithm.DOUBLE_ALTERNATE))
    }

    /**
     * This was a bug where order of loaded rows was different between debug and run configs o_O
     */
    @Test
    @Throws(IOException::class)
    fun canLoadFromFileResourceAndGetALloydsAccount() {
        val weightRows = ModulusWeightRows.fromFile("file/valacdos.txt")

        val ba = BankAccount.with("309070", "12345668")

        val found = weightRows.FindFor(ba)

        assertThat(found.firstWeightRow!!.exception, `is`(equalTo(2)))
        assertThat(found.secondWeightRow!!.exception, `is`(equalTo(9)))
    }
}
