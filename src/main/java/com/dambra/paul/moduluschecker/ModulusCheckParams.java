package com.dambra.paul.moduluschecker;

import java.util.Optional;

public class ModulusCheckParams {
    public final BankAccount account;
    public final Optional<WeightRow> firstWeightRow;
    public final Optional<WeightRow> secondWeightRow;

    public ModulusCheckParams(BankAccount account, Optional<WeightRow> firstWeightRow, Optional<WeightRow> secondWeightRow) {
        this.account = account;
        this.firstWeightRow = firstWeightRow;
        this.secondWeightRow = secondWeightRow;
    }
}
