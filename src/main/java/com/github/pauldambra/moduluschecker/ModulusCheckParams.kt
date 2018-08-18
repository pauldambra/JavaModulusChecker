package com.github.pauldambra.moduluschecker

import com.github.pauldambra.moduluschecker.account.BankAccount
import com.github.pauldambra.moduluschecker.chain.ModulusResult
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow

data class ModulusCheckParams(
  val account: BankAccount,
  val firstWeightRow: WeightRow?,
  val secondWeightRow: WeightRow?,
  val modulusResult: ModulusResult?
) {
    constructor(account: BankAccount):
      this(account, null, null, null)

    constructor(account: BankAccount, firstWeightRow: WeightRow?):
      this(account, firstWeightRow, null, null)

    constructor(account: BankAccount, firstWeightRow: WeightRow?, secondWeightRow: WeightRow?):
      this(account, firstWeightRow, secondWeightRow, null)

    override fun toString(): String {
        return "ModulusCheckParams{account=$account, firstWeightRow=$firstWeightRow, secondWeightRow=$secondWeightRow, modulusResult=$modulusResult${'}'}"
    }

}
