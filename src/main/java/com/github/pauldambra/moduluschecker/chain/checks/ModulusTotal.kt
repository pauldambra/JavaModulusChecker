package com.github.pauldambra.moduluschecker.chain.checks

import com.github.pauldambra.moduluschecker.As
import com.github.pauldambra.moduluschecker.account.BankAccount
import com.google.common.collect.Streams

internal object ModulusTotal {

    fun calculate(account: BankAccount, weights: List<Int>): Int {
        return Streams
          .zip(
            account.allDigits(),
            weights.stream()
          ) { l, r -> l!! * r!! }
          .reduce(0) { a, b -> Integer.sum(a, b) }
    }

    fun calculateDoubleAlternate(account: BankAccount, weights: List<Int>): Int {
        return Streams
          .zip(
            account.allDigits(),
            weights.stream()
          ) { l, r -> l!! * r!! }
          .map<String> { it.toString() }
          .flatMap<Int> { As.integerStream(it) }
          .reduce(0) { a, b -> Integer.sum(a, b) }
    }
}
