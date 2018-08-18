package com.github.pauldambra.moduluschecker.chain.transformers

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

class ExceptionEightAccountTransformer(
  private val next: ModulusChainLink,
  private val rowSelector: (mcp: ModulusCheckParams) -> WeightRow) : ModulusChainLink {

    override fun check(params: ModulusCheckParams): ModulusResult {
        var paramsToPassAlong = params
        if (rowSelector(paramsToPassAlong).isException(8)) {
            val account = BankAccount.with("090126", paramsToPassAlong.account.accountNumber)
            paramsToPassAlong = paramsToPassAlong.copy(account = account)
        }
        return next.check(paramsToPassAlong)
    }
}
