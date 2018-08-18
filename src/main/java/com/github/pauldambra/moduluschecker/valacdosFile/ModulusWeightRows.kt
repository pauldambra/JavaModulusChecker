package com.github.pauldambra.moduluschecker.valacdosFile

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.google.common.base.Charsets
import com.google.common.io.Resources
import java.io.IOException

class ModulusWeightRows(private val valacdosRows: List<ValacdosRow>) {

    fun FindFor(account: BankAccount): ModulusCheckParams {

        var first: WeightRow? = null
        var second: WeightRow? = null

        for (vr in this.valacdosRows) {
            val stillSeekingFirstMatch = first == null

            if (!vr.sortCodeRange.contains(account.sortCode)) {
                if (stillSeekingFirstMatch) {
                    continue
                } else {
                    break
                }
            }

            if (stillSeekingFirstMatch) {
                first = vr.weightRow
                continue
            }

            second = vr.weightRow
        }

        return if (first != null && second != null) {
            ModulusCheckParams(account, first, second)
        } else if (first != null) {
            ModulusCheckParams(account, first)
        } else {
            ModulusCheckParams(account)
        }
    }

    companion object {

        @Throws(IOException::class)
        fun fromFile(filePath: String): ModulusWeightRows {

            val url = Resources.getResource(filePath)
            val text = Resources.toString(url, Charsets.UTF_8)

            val valacdosRows = text
              .split("\r?\n".toRegex())
              .filter { it.isNotBlank() }
              .filter { it.isNotEmpty() }
              .fold(emptyList<ValacdosRow>()) {
                  acc, element ->
                  val sortCodeRange = SortCodeRange.parse(element)
                  val weightRow = WeightRow.parse(element)

                  if (weightRow != null) {
                      acc + listOf(ValacdosRow(sortCodeRange, weightRow))
                  } else {
                      acc
                  }
              }

            return ModulusWeightRows(valacdosRows)
        }
    }

}
