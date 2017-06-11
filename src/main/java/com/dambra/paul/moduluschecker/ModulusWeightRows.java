package com.dambra.paul.moduluschecker;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ModulusWeightRows {

    private static final Splitter newlineSplitter = Splitter.on(Pattern.compile("\r?\n"));

    private final ImmutableListMultimap<String, WeightRow> weights;

    public ModulusWeightRows(ImmutableMap<SortCodeRange, WeightRow> weights) {
        this.weights = expand(weights);
    }

    private ImmutableListMultimap<String, WeightRow> expand(ImmutableMap<SortCodeRange, WeightRow> weights) {
        ListMultimap<String, WeightRow> expandedRows = ArrayListMultimap.create();
        weights.forEach((scr, weightRow) -> scr.fullRange
                                               .forEach(sc -> expandedRows.put(sc, weightRow)));
        return ImmutableListMultimap.copyOf(expandedRows);
    }

    public ModulusCheckParams FindFor(BankAccount account) {

        if (!weights.containsKey(account.sortCode)) {
            return new ModulusCheckParams(account, Optional.empty());
        }

        return new ModulusCheckParams(account, Optional.of(weights.get(account.sortCode)));
    }

    public static Optional<ModulusWeightRows> fromFile(String filePath) {
        URL url = Resources.getResource(filePath);

        String text;
        try {
            text = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
            // TODO how do I log?
            System.out.println("NO CONTENTS :( " + e.getMessage());
            return Optional.empty();
        }

        Map<SortCodeRange, WeightRow> rows =
            newlineSplitter
                .splitToList(text)
                .stream()
                .filter(r -> !r.isEmpty())
                .map(r -> new Object() {
                    Optional<SortCodeRange> sortCodeRange = SortCodeRange.parse(r);
                    Optional<WeightRow> weightRow = WeightRow.parse(r);
                })
                .filter(r -> r.weightRow.isPresent())
                .filter(r -> r.sortCodeRange.isPresent())
                .collect(Collectors.toMap(
                        c -> c.sortCodeRange.get(),
                        c -> c.weightRow.get()
                ));

        return Optional.of(new ModulusWeightRows(ImmutableMap.copyOf(rows)));
    }
}
