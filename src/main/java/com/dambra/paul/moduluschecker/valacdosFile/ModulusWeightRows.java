package com.dambra.paul.moduluschecker.valacdosFile;

import com.dambra.paul.moduluschecker.Account.BankAccount;
import com.dambra.paul.moduluschecker.ModulusCheckParams;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModulusWeightRows {

    private static final Splitter newlineSplitter = Splitter.on(Pattern.compile("\r?\n"));
    private final ImmutableList<ValacdosRow> valacdosRows;

    public ModulusWeightRows(List<ValacdosRow> valacdosRows) {
        this.valacdosRows = ImmutableList.copyOf(valacdosRows);
    }

    public ModulusCheckParams FindFor(BankAccount account) {

        WeightRow first = null;
        WeightRow second = null;

        for (ValacdosRow vr : this.valacdosRows) {
            boolean stillSeekingFirstMatch = first == null;

            if (!vr.getSortCodeRange().contains(account.sortCode)) {
                if (stillSeekingFirstMatch) {
                    continue;
                } else {
                    break;
                }
            }

            if (stillSeekingFirstMatch) {
                first = vr.getWeightRow();
                continue;
            }

            second = vr.getWeightRow();
        }

        return new ModulusCheckParams(
                account,
                Optional.ofNullable(first),
                Optional.ofNullable(second),
                Optional.empty());
    }

    public static Optional<ModulusWeightRows> fromFile(String filePath) {
        URL url = Resources.getResource(filePath);

        String text;
        try {
            text = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            System.out.println("NO CONTENTS :( " + e.getMessage());
            return Optional.empty();
        }

        List<ValacdosRow> valacdosRows = newlineSplitter
                .splitToList(text)
                .stream()
                .filter(r -> !r.isEmpty())
                .map(r -> new Object() {
                    Optional<SortCodeRange> sortCodeRange = SortCodeRange.parse(r);
                    Optional<WeightRow> weightRow = WeightRow.parse(r);
                })
                .filter(r -> r.weightRow.isPresent())
                .filter(r -> r.sortCodeRange.isPresent())
                .map(r -> new ValacdosRow(r.sortCodeRange.get(), r.weightRow.get()))
                .collect(Collectors.toList());

        return Optional.of(new ModulusWeightRows(valacdosRows));
    }

}
