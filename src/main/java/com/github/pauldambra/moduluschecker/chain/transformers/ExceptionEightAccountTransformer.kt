package com.github.pauldambra.moduluschecker.chain.transformers

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionEightAccountTransformer(
  private val next: ModulusChainLink,
  private val rowSelector: (mcp: ModulusCheckParams) -> WeightRow) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (!rowSelector(params).hasException(8)) {
          next.check(params)
      } else {
          val account = BankAccount("090126", params.account.accountNumber)
          next.check(params.copy(account = account))
      }
}
