package com.github.pauldambra.moduluschecker;

import com.github.pauldambra.moduluschecker.Account.BankAccount;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SortCodeSubstitution {

    private static final Splitter newlineSplitter = Splitter.on(Pattern.compile("\r?\n"));
    private static final Splitter spaceSplitter = Splitter.on(CharMatcher.whitespace()).omitEmptyStrings();

    private final ImmutableMap<String, String> sortCodeSubstitutionTable;

    public SortCodeSubstitution(ImmutableMap<String, String> sortCodeSubstitutionTable) {
        this.sortCodeSubstitutionTable = sortCodeSubstitutionTable;
    }

    private SortCodeSubstitution(Map<String, String> sortCodeSubstitutionTable) {
        this.sortCodeSubstitutionTable = ImmutableMap.copyOf(sortCodeSubstitutionTable);
    }

    public BankAccount Apply(BankAccount bankAccount) {
        return sortCodeSubstitutionTable.containsKey(bankAccount.sortCode)
                ? BankAccount.Of(sortCodeSubstitutionTable.get(bankAccount.sortCode), bankAccount.accountNumber)
                : new BankAccount(bankAccount);
    }

    public static SortCodeSubstitution fromFile(String filePath) throws IOException {

        URL url = Resources.getResource(filePath);
        String text = Resources.toString(url, Charsets.UTF_8);

        Map<String, String> substitutions = newlineSplitter
                                                .splitToList(text)
                                                .stream()
                                                .map(spaceSplitter::splitToList)
                                                .filter(r -> r.size() != 0)
                                                .collect(Collectors.toMap(
                                                        ss -> ss.get(0),
                                                        ss -> ss.get(1)
                                                ));

        return new SortCodeSubstitution(substitutions);
    }
}
