package com.github.pauldambra.moduluschecker.chain.transformers

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionSevenAccountTransformer(
  private val next: ModulusChainLink,
  private val rowSelector: (mcp: ModulusCheckParams) -> WeightRow) : ModulusChainLink {

    override fun check(params: ModulusCheckParams): ModulusResult {
        var paramsToPassAlong = params
        if (rowSelector(paramsToPassAlong).isException(7)) {
            val account = paramsToPassAlong.account.zeroiseUToB()
            paramsToPassAlong = paramsToPassAlong.copy(account = account)
        }
        return next.check(paramsToPassAlong)
    }
}
