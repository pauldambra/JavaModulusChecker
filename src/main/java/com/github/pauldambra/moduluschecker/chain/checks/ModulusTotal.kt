package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.toNumberList

internal object ModulusTotal {

    fun calculate(account: BankAccount, weights: List<Int>) =
      account
          .allDigits()
          .zip(weights) { l, r -> l * r }
          .reduce() { a, b -> a + b }

    fun calculateDoubleAlternate(account: BankAccount, weights: List<Int>): Int {
        return account.allDigits().zip(weights) { l, r -> l * r }
          .map { it.toString() }
          .flatMap { it.toNumberList() }
          .reduce() { a, b -> a +b }
    }
}
