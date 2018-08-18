package com.github.pauldambra.moduluschecker

enum class ModulusAlgorithm {
    MOD10, MOD11, DOUBLE_ALTERNATE;

    companion object {

        @Throws(UnknownAlgorithmException::class)
        fun parse(s: String) =
          when (s) {
              "MOD10" -> MOD10
              "MOD11" -> MOD11
              "DBLAL" -> DOUBLE_ALTERNATE
              else    -> throw UnknownAlgorithmException("Cannot parse $s as a modulus algorithm")
          }
    }
}
