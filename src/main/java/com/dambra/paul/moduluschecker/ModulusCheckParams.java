package com.dambra.paul.moduluschecker;

import java.util.Optional;

public class ModulusCheckParams {
    public final BankAccount account;
    public final Optional<WeightRow> weightRow;

    public ModulusCheckParams(BankAccount account, Optional<WeightRow> weightRow) {
        this.account = account;
        this.weightRow = weightRow;
    }
}
