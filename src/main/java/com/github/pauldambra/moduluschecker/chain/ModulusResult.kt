package com.github.pauldambra.moduluschecker.chain

data class ModulusResult(
  val firstCheckResult: Boolean?,
  val secondCheckResult: Boolean?,
  val firstException: Int?,
  val secondException: Int?
) {

    private val isExceptionFive: Boolean
        get() = firstException == 5

    private val isExceptionTen: Boolean
        get() = firstException == 10

    private val isExceptionTwelve: Boolean
        get() = firstException == 12

    constructor(firstCheckResult: Boolean?) :
      this(firstCheckResult, null, null, null)

    constructor(firstCheckResult: Boolean?, secondCheckResult: Boolean?) :
      this(firstCheckResult, secondCheckResult, null, null)

    fun processResults(): Boolean {
        return when {
            firstCheck()          -> if (secondCheckResult != null) processTwoCheckResult() else firstCheck()
            requiresSecondCheck() -> processTwoCheckResult()
            else                  -> false
        }
    }

    private fun requiresSecondCheck() = exceptionsThatRequireSecondCheck.contains(firstException ?: -1)

    private fun processTwoCheckResult() =
      when {
          isExceptionFive                     -> bothMustPass()
          isExceptionTen || isExceptionTwelve -> firstOrSecond()
          else                                -> secondCheck()
      }

    private fun firstCheck(): Boolean {
        return firstCheckResult ?: false
    }

    private fun secondCheck(): Boolean {
        return secondCheckResult ?: false
    }

    private fun firstOrSecond(): Boolean {
        return firstCheck() || secondCheck()
    }

    private fun bothMustPass(): Boolean {
        return firstCheck() && secondCheck()
    }

    companion object {

        /** the default is for a check to pass  */
        val PASSES = ModulusResult(true, true)
        val FAILS = ModulusResult(false, false)

        val exceptionsThatRequireSecondCheck: List<Int> = listOf(2, 5, 9, 10, 11, 12, 13, 14)
    }
}
