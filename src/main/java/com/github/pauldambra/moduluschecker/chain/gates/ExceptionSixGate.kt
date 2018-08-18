package com.github.pauldambra.moduluschecker.chain.gates

import com.github.pauldambra.moduluschecker.ModulusCheckParams
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.ModulusChainLink
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.chain.transformers.ExceptionSevenAccountTransformer
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow
import java.util.*

/**
 * Exception 6
 * Indicates that these sorting codes may contain foreign currency accounts which cannot be checked.
 * Perform the first and second checks, except:
 * • If a = 4, 5, 6, 7 or 8, and g and h are the same, the accounts are for a foreign currency and the
 * checks cannot be used.
 */
class ExceptionSixGate(private val next: ExceptionSevenAccountTransformer) : ModulusChainLink {

    override fun check(params: ModulusCheckParams) =
      if (WeightRow.isExceptionSix(params.firstWeightRow) && isForeignCurrencyAccount(params.account)) {
          ModulusResult(true, null)
      } else {
          next.check(params)
      }

    private fun isForeignCurrencyAccount(account: BankAccount): Boolean {
        val a = account.getNumberAt(BankAccount.A)
        val g = account.getNumberAt(BankAccount.G)
        val h = account.getNumberAt(BankAccount.H)
        return Arrays.asList(4, 5, 6, 7, 8).contains(a) && g == h
    }

}
