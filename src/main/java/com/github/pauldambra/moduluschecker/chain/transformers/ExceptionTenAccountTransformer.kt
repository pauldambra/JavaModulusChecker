package com.github.pauldambra.moduluschecker.chain.transformers

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.FirstModulusCheckRouter
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink

class ExceptionTenAccountTransformer(private val next: FirstModulusCheckRouter) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (params.firstWeightRow!!.isException(10) && shouldZeroiseUToB(params)) {
          val account = params.account.zeroiseUToB()
          next.check(params.copy(account = account))
      } else {
          next.check(params)
      }

    // if ab = 09 or ab = 99 and g = 9, zeroise weighting positions u-b.
    private fun shouldZeroiseUToB(params: ModulusCheckParams): Boolean {
        val a = params.account.getNumberAt(BankAccount.A)
        val b = params.account.getNumberAt(BankAccount.B)
        val g = params.account.getNumberAt(BankAccount.G)

        return (a == 0 || a == 9) && b == 9 && g == 9
    }
}
