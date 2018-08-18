package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow


class DoubleAlternateCheck {
    fun check(
      params: ModulusCheckParams,
      rowSelector: (mcp: ModulusCheckParams) -> WeightRow): Boolean {
        val selectedRow = rowSelector(params)
        var total = ModulusTotal.calculateDoubleAlternate(params.account, selectedRow.weights)

        if (selectedRow.isException(1)) {
            total += 27
        }

        val remainder = total % 10
        return remainder == 0
    }
}