package com.dambra.paul.moduluschecker;

import com.google.common.collect.ImmutableList;

import java.util.Optional;

public class ModulusCheckParams {
    public final BankAccount account;
    public final Optional<ImmutableList<WeightRow>> weightRows;

    public ModulusCheckParams(BankAccount account, Optional<ImmutableList<WeightRow>> weightRows) {
        this.account = account;
        this.weightRows = weightRows;
    }
}
