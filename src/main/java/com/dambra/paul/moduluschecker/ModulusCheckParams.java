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

    static ModulusCheckParams startingParams(BankAccount account) {
        return new ModulusCheckParams(account, Optional.empty(), Optional.empty(), Optional.empty());
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

    public ModulusCheckParams withResult(ModulusResult result) {
        return new ModulusCheckParams(
                account,
                firstWeightRow,
                secondWeightRow,
                Optional.ofNullable(result)
        );
    }

    public boolean isExceptionSix() {
        return firstWeightRow.isPresent()
                && firstWeightRow.get().isException(6);
    }

    public boolean firstCheckPassed() {
        return modulusResult.isPresent()
                && modulusResult.get().firstCheckResult.orElse(false);
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
