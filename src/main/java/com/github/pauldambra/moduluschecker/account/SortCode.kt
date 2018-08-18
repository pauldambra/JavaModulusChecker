package com.github.pauldambra.moduluschecker.account

// I didn't log where I got this data... is it correct?
// todo allow external hookup of ISCD? Can't find a free version
internal object SortCode {
    fun IsCooperativeBankSortCode(sortCode: String): Boolean {
        return sortCode.startsWith("08") || sortCode.startsWith("839")
    }

    fun IsNatWestSortCode(sortCode: String): Boolean {
        return (sortCode.startsWith("600")
          || sortCode.startsWith("606")
          || sortCode.startsWith("601")
          || sortCode.startsWith("609")
          || sortCode.startsWith("830")
          || sortCode.startsWith("602"))
    }
}
