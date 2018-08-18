package com.github.pauldambra.moduluschecker.valacdosFile

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.google.common.collect.ImmutableList

object ModulusElevenWeightsTransformer {
    fun checkForExceptionTwoAndNine(
      params: ModulusCheckParams, selectedRow: WeightRow): List<Int> {

        if (!WeightRow.isExceptionTwoAndNine(
            params.firstWeightRow,
            params.secondWeightRow)) {
            return selectedRow.weights
        }

        if (params.account.getNumberAt(BankAccount.A) == 0) {
            return selectedRow.weights
        }

        return if (params.account.getNumberAt(BankAccount.G) == 9) {
            ImmutableList.of(0, 0, 0, 0, 0, 0, 0, 0, 8, 7, 10, 9, 3, 1)
        } else {
            ImmutableList.of(0, 0, 1, 2, 5, 3, 6, 4, 8, 7, 10, 9, 3, 1)
        }
    }
}
