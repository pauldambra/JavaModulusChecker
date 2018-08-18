package com.github.pauldambra.moduluschecker.chain.transformers

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.FirstModulusCheckRouter
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult

class ExceptionTenAccountTransformer(private val next: FirstModulusCheckRouter) : ModulusChainLink {

    override fun check(params: ModulusCheckParams): ModulusResult {
        var paramsToPassAlong = params

        if (paramsToPassAlong.firstWeightRow!!.isException(10) && shouldZeroiseUToB(paramsToPassAlong)) {
            val account = paramsToPassAlong.account.zeroiseUToB()
            paramsToPassAlong = paramsToPassAlong.copy(account = account)
        }
        return next.check(paramsToPassAlong)
    }

    // if ab = 09 or ab = 99 and g = 9, zeroise weighting positions u-b.
    private fun shouldZeroiseUToB(params: ModulusCheckParams): Boolean {
        val a = params.account.getNumberAt(BankAccount.A)
        val b = params.account.getNumberAt(BankAccount.B)
        val g = params.account.getNumberAt(BankAccount.G)

        return (a == 0 || a == 9) && b == 9 && g == 9
    }
}
