package com.github.pauldambra.moduluschecker.chain.transformers

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

object ModulusElevenWeightsTransformer {
    fun checkForExceptionTwoAndNine(params: ModulusCheckParams, selectedRow: WeightRow) =
      when {
          notExceptionTwoAndNine(params)                 -> selectedRow.weights
          params.account.getNumberAt(BankAccount.A) == 0 -> selectedRow.weights
          params.account.getNumberAt(BankAccount.G) == 9 -> listOf(0, 0, 0, 0, 0, 0, 0, 0, 8, 7, 10, 9, 3, 1)
          else                                           -> listOf(0, 0, 1, 2, 5, 3, 6, 4, 8, 7, 10, 9, 3, 1)
      }

    private fun notExceptionTwoAndNine(params: ModulusCheckParams) =
      !WeightRow.isExceptionTwoAndNine(params.firstWeightRow, params.secondWeightRow)
}
