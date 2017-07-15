package com.github.pauldambra.moduluschecker;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.github.pauldambra.moduluschecker.chain.ModulusResult;
import com.github.pauldambra.moduluschecker.valacdosFile.WeightRow;

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

    static ModulusCheckParams startingParams(BankAccount account) {
        return new ModulusCheckParams(account, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public ModulusCheckParams withAccount(BankAccount bankAccount) {
        return new ModulusCheckParams(
            bankAccount,
            firstWeightRow,
            secondWeightRow,
            modulusResult
        );
    }

    public ModulusCheckParams withResult(ModulusResult result) {
        return new ModulusCheckParams(
                account,
                firstWeightRow,
                secondWeightRow,
                Optional.ofNullable(result)
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
