package com.dambra.paul.moduluschecker;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SortCodeRange {
    private static final Splitter splitter = Splitter.on(CharMatcher.whitespace())
            .omitEmptyStrings();

    private static final int LOWEST_SORT_CODE_INDEX = 0;
    private static final int HIGHEST_SORT_CODE_INDEX = 1;

    public Stream<String> fullRange;

    public SortCodeRange(String lowest, String highest) {
        GenerateRangeBetween(lowest, highest);
    }

    private void GenerateRangeBetween(String lowest, String highest) {
        int start = Integer.parseInt(lowest.replaceAll("^0+", ""));
        int end = Integer.parseInt(highest.replaceAll("^0+", ""));

         fullRange = IntStream.rangeClosed(start, end)
                              .mapToObj(String::valueOf)
                              .map(s -> Strings.padStart(s, 6, '0'));
    }

    public static Optional<SortCodeRange> parse(String input) {
        List<String> parts = splitter.splitToList(input);

        if (parts.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(
                new SortCodeRange(
                        parts.get(LOWEST_SORT_CODE_INDEX),
                        parts.get(HIGHEST_SORT_CODE_INDEX)));
    }
}
