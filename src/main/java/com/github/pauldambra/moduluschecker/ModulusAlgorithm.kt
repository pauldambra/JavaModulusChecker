package com.github.pauldambra.moduluschecker

enum class ModulusAlgorithm {
    MOD10, MOD11, DOUBLE_ALTERNATE;


    companion object {

        @Throws(UnknownAlgorithmException::class)
        fun parse(s: String): ModulusAlgorithm {
            when (s) {
                "MOD10" -> return MOD10
                "MOD11" -> return MOD11
                "DBLAL" -> return DOUBLE_ALTERNATE
                else    -> throw UnknownAlgorithmException("Cannot parse $s as a modulus algorithm")
            }
        }
    }
}
