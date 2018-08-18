package com.github.pauldambra.moduluschecker.chain

import com.google.common.collect.ImmutableList

class ModulusResult {

    val firstCheckResult: Boolean?
    val secondCheckResult: Boolean?
    val firstException: Int?
    val secondException: Int?

    private val isExceptionFive: Boolean
        get() = firstException == 5

    private val isExceptionTen: Boolean
        get() = firstException == 10

    private val isExceptionTwelve: Boolean
        get() = firstException == 12

    constructor(firstCheckResult: Boolean?, secondCheckResult: Boolean?) {
        this.firstCheckResult = firstCheckResult
        this.secondCheckResult = secondCheckResult

        firstException = null
        secondException = null
    }

    private constructor(
      firstCheckResult: Boolean?,
      secondCheckResult: Boolean?,
      firstException: Int?,
      secondException: Int?) {
        this.firstCheckResult = firstCheckResult
        this.secondCheckResult = secondCheckResult
        this.firstException = firstException
        this.secondException = secondException
    }

    fun withFirstException(rowException: Int?): ModulusResult {
        return ModulusResult(firstCheckResult, secondCheckResult, rowException, null)
    }

    fun withSecondException(rowException: Int?): ModulusResult {
        return ModulusResult(firstCheckResult, secondCheckResult, firstException, rowException)
    }

    fun processResults(): Boolean? {
        if (firstCheck()) {
            return if (secondCheckResult != null)
                processTwoCheckResult()
            else
                firstCheck()
        }

        return if (exceptionsThatRequireSecondCheck.contains(firstException ?: -1))
            processTwoCheckResult()
        else
            false
    }

    private fun processTwoCheckResult(): Boolean? {
        if (isExceptionFive) {
            return bothMustPass()
        }

        return if (isExceptionTen || isExceptionTwelve) {
            firstOrSecond()
        } else secondCheck()

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

    override fun toString(): String {
        return "ModulusResult{" +
          "firstCheckPassed=" + firstCheckResult +
          ", secondCheckResult=" + secondCheckResult +
          ", firstException=" + firstException +
          ", secondException=" + secondException +
          '}'.toString()
    }

    companion object {

        /** the default is for a check to pass  */
        val PASSES = ModulusResult(true, true)

        val exceptionsThatRequireSecondCheck: List<Int> = ImmutableList.of(2, 5, 9, 10, 11, 12, 13, 14)

        internal fun WithFirstResult(result: Boolean): ModulusResult {
            return ModulusResult(result, null)
        }

        fun withSecondResult(modulusResult: ModulusResult?, result: Boolean?): ModulusResult {
            return ModulusResult(
              modulusResult?.firstCheckResult ?: false,
              result,
              modulusResult?.firstException,
              modulusResult?.secondException)
        }

        fun firstCheckPassed(modulusResult: ModulusResult?): Boolean {
            return modulusResult?.firstCheck() ?: false
        }

        fun copy(original: ModulusResult?): ModulusResult? {
            return if (original == null) {
                null
            } else ModulusResult(
              original.firstCheckResult, original.secondCheckResult, original.firstException, original.secondException
            )
        }
    }
}
