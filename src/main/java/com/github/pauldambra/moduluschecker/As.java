package com.github.pauldambra.moduluschecker;

import java.util.stream.Stream;

public final class As {
    public static Stream<Integer> integerStream(String s) {
        return s.chars()
                .mapToObj(i -> (char) i)
                .map(String::valueOf)
                .map(Integer::parseInt);
    }
}
