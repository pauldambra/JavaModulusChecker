package com.github.pauldambra.moduluschecker.chain.transformers

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionSevenAccountTransformer(
  private val next: ModulusChainLink,
  private val rowSelector: (mcp: ModulusCheckParams) -> WeightRow) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (!rowSelector(params).hasException(7)) {
          next.check(params)
      } else {
          val account = params.account.zeroiseUToB()
          next.check(params.copy(account = account))
      }
}
