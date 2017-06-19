package com.dambra.paul.moduluschecker;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.chain.ModulusResult;
import com.dambra.paul.moduluschecker.valacdosFile.WeightRow;

import java.util.Optional;

public final class ModulusCheckParams {
    private final BankAccount account;
    private final Optional<WeightRow> firstWeightRow;
    private final Optional<WeightRow> secondWeightRow;
    private final Optional<ModulusResult> modulusResult;

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

    public BankAccount getAccount() {
        return account;
    }

    public Optional<WeightRow> getFirstWeightRow() {
        return Optional.ofNullable(
                WeightRow.copy(firstWeightRow.orElse(null)));
    }

    public Optional<WeightRow> getSecondWeightRow() {
        return Optional.ofNullable(
                WeightRow.copy(secondWeightRow.orElse(null)));
    }

    public Optional<ModulusResult> getModulusResult() {
        return Optional.ofNullable(ModulusResult.copy(modulusResult.orElse(null)));
    }

    public ModulusCheckParams withAccount(BankAccount bankAccount) {
        return new ModulusCheckParams(
                bankAccount,
                getFirstWeightRow(),
                getSecondWeightRow(),
                getModulusResult()
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
