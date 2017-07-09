package com.dambra.paul.moduluschecker.valacdosFile;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.List;
import java.util.Optional;

public final class SortCodeRange {
    private static final Splitter splitter = Splitter.on(CharMatcher.whitespace())
            .omitEmptyStrings();

    private final int start;
    private final int end;

    public SortCodeRange(String lowest, String highest) {
        start = asInteger(lowest);
        end = asInteger(highest);
    }

    private int asInteger(String s) {
        return Integer.parseInt(s.replaceAll("^0+", ""));
    }

    static Optional<SortCodeRange> parse(String input) {
        List<String> parts = splitter.splitToList(input);

        if (parts.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                new SortCodeRange(
                        parts.get(WeightRow.LOWEST_SORT_CODE_INDEX),
                        parts.get(WeightRow.HIGHEST_SORT_CODE_INDEX)));
    }

    public boolean contains(String sortCode) {
        int i = asInteger(sortCode);
        return start <= i && i <= end;
    }
}
