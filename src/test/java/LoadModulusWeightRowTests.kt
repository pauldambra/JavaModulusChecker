

import com.github.pauldambra.moduluschecker.ModulusAlgorithm
import com.github.pauldambra.moduluschecker.UnknownAlgorithmException
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.ModulusWeightRows
import com.github.pauldambra.moduluschecker.valacdosFile.SortCodeRange
import com.github.pauldambra.moduluschecker.valacdosFile.ValacdosRow
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import java.io.IOException
import java.util.*

class LoadModulusWeightRowTests {

    @Test
    @Throws(UnknownAlgorithmException::class)
    fun canParseAModulusWeightRow() {
        val input = "074456 074456 MOD10    0    3    2    4    5    8    9    4    5    6    7    8    9   -1  13"

        val row = WeightRow.parse(input)

        assert.that(row?.modulusAlgorithm, equalTo(ModulusAlgorithm.MOD10))
        assert.that(row?.exception, equalTo(13))
        assert.that(row?.weights, equalTo(listOf(0, 3, 2, 4, 5, 8, 9, 4, 5, 6, 7, 8, 9, -1)))
    }

    @Test
    fun canLoadModulusWeightRow() {
        val weightRow = WeightRow(ModulusAlgorithm.MOD10, listOf(), null)
        val sortCodeRange = SortCodeRange("012344", "012346")

        val modulusRows = ModulusWeightRows(Arrays.asList(ValacdosRow(sortCodeRange, weightRow)))

        val originalAccount = BankAccount("012345", "01234567")
        val modulusCheckParams = modulusRows.findFor(originalAccount)

        assert.that(modulusCheckParams.account, equalTo(originalAccount))
        assert.that(modulusCheckParams.firstWeightRow!!, equalTo(weightRow))
    }

    @Test
    fun returnsUnmatchedRowWhenNoSortCodeRangeMatches() {
        val weightRow = WeightRow(ModulusAlgorithm.MOD10, listOf(), null)
        val sortCodeRange = SortCodeRange("012346", "012347")

        val modulusRows = ModulusWeightRows(Arrays.asList(ValacdosRow(sortCodeRange, weightRow)))

        val originalAccount = BankAccount("012345", "01234567")
        val modulusCheckParams = modulusRows.findFor(originalAccount)

        assert.that(modulusCheckParams.firstWeightRow, equalTo(null as WeightRow?))
    }

    @Test
    @Throws(IOException::class)
    fun canLoadFromFileResource() {
        val weightRows = ModulusWeightRows.fromFile("file/valacdos.txt")

        val ba = BankAccount("938173", "01234567")

        val found = weightRows.findFor(ba)

        val modulusAlgorithms = listOf(
          found.firstWeightRow!!.modulusAlgorithm,
          found.secondWeightRow!!.modulusAlgorithm
        )
        assert.that(modulusAlgorithms, equalTo(listOf(ModulusAlgorithm.MOD11, ModulusAlgorithm.DOUBLE_ALTERNATE)))
    }

    /**
     * This was a bug where order of loaded rows was different between debug and run configs o_O
     */
    @Test
    @Throws(IOException::class)
    fun canLoadFromFileResourceAndGetALloydsAccount() {
        val weightRows = ModulusWeightRows.fromFile("file/valacdos.txt")

        val ba = BankAccount("309070", "12345668")

        val found = weightRows.findFor(ba)

        assert.that(found.firstWeightRow!!.exception, equalTo(2))
        assert.that(found.secondWeightRow!!.exception, equalTo(9))
    }
}
