package com.github.pauldambra.moduluschecker

import java.util.stream.Stream

object As {
    fun integerStream(s: String): Stream<Int> {
        return s.chars()
          .mapToObj { i -> i.toChar() }
          .map<String> { it.toString() }
          .map { Integer.parseInt(it) }
    }
}
