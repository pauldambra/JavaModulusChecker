package com.github.pauldambra.moduluschecker.valacdosFile

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount

class ModulusWeightRows(private val valacdosRows: List<ValacdosRow>) {

    fun findFor(account: BankAccount): ModulusCheckParams {

        val candidates =
          valacdosRows
            .filter { it.sortCodeRange.contains(account.sortCode) }
            .take(2)
            .map { it.weightRow }

        return ModulusCheckParams(
          account,
          candidates.getOrNull(0),
          candidates.getOrNull(1)
        )
    }

    companion object {

        fun fromFile(filePath: String): ModulusWeightRows {

            val valacdosRows =
              this::class.java.classLoader.getResource(filePath).readText()
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
