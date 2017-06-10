package com.dambra.paul.moduluschecker;

public class ModulusCheckParams {
    public final BankAccount account;
    public final WeightRow weightRow;

    public ModulusCheckParams(BankAccount account, WeightRow weightRow) {
        this.account = account;
        this.weightRow = weightRow;
    }
}
