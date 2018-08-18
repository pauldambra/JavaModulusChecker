package com.github.pauldambra.moduluschecker.valacdosFile

data class SortCodeRange(val lowest: String, val highest: String) {

    private val start: Int
    private val end: Int

    init {
        start = asInteger(lowest)
        end = asInteger(highest)
    }

    private fun asInteger(s: String) = Integer.parseInt(s.replace("^0+".toRegex(), ""))

    operator fun contains(sortCode: String) = asInteger(sortCode) in start..end

    companion object {

        private const val LOWEST_SORT_CODE_INDEX = 0
        private const val HIGHEST_SORT_CODE_INDEX = 1

        internal fun parse(input: String): SortCodeRange {
            val parts = input
              .split(" ")
              .filter { it.isNotBlank() }

            return SortCodeRange(
                  parts[LOWEST_SORT_CODE_INDEX],
                  parts[HIGHEST_SORT_CODE_INDEX])
        }
    }
}
