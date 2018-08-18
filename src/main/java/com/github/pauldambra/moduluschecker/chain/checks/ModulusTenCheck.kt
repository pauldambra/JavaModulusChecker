package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ModulusTenCheck {

    fun check(params: ModulusCheckParams, rowSelector: (mcp: ModulusCheckParams) ->WeightRow): Boolean {
        val selectedRow = rowSelector(params)

        val total = ModulusTotal.calculate(params.account, selectedRow.weights)

        return total % 10 == 0
    }

}
