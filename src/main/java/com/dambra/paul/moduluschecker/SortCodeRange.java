package com.dambra.paul.moduluschecker;

import com.google.common.base.Strings;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SortCodeRange {
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
}
