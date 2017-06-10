package com.dambra.paul.moduluschecker;

import com.google.common.collect.ImmutableMap;

public class SortCodeSubstitution {

    private final ImmutableMap<String, String> sortCodeSubstitutionTable;

    public SortCodeSubstitution(ImmutableMap<String, String> sortCodeSubstitutionTable) {

        this.sortCodeSubstitutionTable = sortCodeSubstitutionTable;
    }

    public BankAccount Apply(BankAccount bankAccount) {
        return sortCodeSubstitutionTable.containsKey(bankAccount.sortCode)
                ? new BankAccount(sortCodeSubstitutionTable.get(bankAccount.sortCode), bankAccount.accountNumber)
                : new BankAccount(bankAccount);
    }
}
