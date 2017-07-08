package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.chain.ModulusResult;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;

public final class ModulusCheckParams {
    public final BankAccount account;
    public final Optional<WeightRow> firstWeightRow;
    public final Optional<WeightRow> secondWeightRow;
    public final Optional<ModulusResult> modulusResult;

    public ModulusCheckParams(
            BankAccount account,
            Optional<WeightRow> firstWeightRow,
            Optional<WeightRow> secondWeightRow,
            Optional<ModulusResult> modulusResult) {
        this.account = account;
        this.firstWeightRow = firstWeightRow;
        this.secondWeightRow = secondWeightRow;
        this.modulusResult = modulusResult;
    }

    public ModulusCheckParams withAccount(BankAccount bankAccount) {
        return new ModulusCheckParams(
                bankAccount,
                Optional.ofNullable(
                        WeightRow.copy(firstWeightRow.orElse(null))),
                Optional.ofNullable(
                        WeightRow.copy(secondWeightRow.orElse(null))),
                Optional.ofNullable(ModulusResult.copy(modulusResult.orElse(null)))
        );
    }

    @Override
    public String toString() {
        return "ModulusCheckParams{" +
                "account=" + account +
                ", firstWeightRow=" + firstWeightRow +
                ", secondWeightRow=" + secondWeightRow +
                ", modulusResult=" + modulusResult +
                '}';
    }

}
