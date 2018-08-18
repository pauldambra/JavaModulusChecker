package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.SecondModulusCheckRouter

class ExceptionThreeGate(private val next: SecondModulusCheckRouter) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (canSkipForExceptionThree(params)) {
          params.modulusResult!!.copy(secondCheckResult = null)
      } else {
          next.check(params)
      }

    private fun canSkipForExceptionThree(params: ModulusCheckParams): Boolean {
        val c = params.account.getNumberAt(BankAccount.C)
        return c == 6 || c == 9
    }
}
