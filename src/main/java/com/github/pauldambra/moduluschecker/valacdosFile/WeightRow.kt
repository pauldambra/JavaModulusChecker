package com.github.pauldambra.moduluschecker.valacdosFile

import com.github.pauldambra.moduluschecker.ModulusAlgorithm

data class WeightRow(val modulusAlgorithm: ModulusAlgorithm, val weights: List<Int>, val exception: Int?) {

    fun hasException(exceptionNumber: Int) = exception == exceptionNumber

    companion object {

        private fun hasException(exceptionNumber: Int, weightRow: WeightRow?) =
          weightRow?.hasException(exceptionNumber) ?: false

        fun isExceptionTwoAndNine(firstRow: WeightRow?, secondRow: WeightRow?) =
          hasException(2, firstRow) && hasException(9, secondRow)

        fun isExceptionFourteen(weightRow: WeightRow?) = hasException(14, weightRow)

        fun isExceptionTwo(weightRow: WeightRow?) = hasException(2, weightRow)

        fun isExceptionFive(weightRow: WeightRow?) = hasException(5, weightRow)

        fun isExceptionSix(weightRow: WeightRow?) = hasException(6, weightRow)

        private const val MODULUS_INDEX = 2
        private const val EXCEPTION_INDEX = 14

        fun parse(candidateRow: String): WeightRow? {

            val candidateParts = candidateRow.split("\\s+".toRegex())
              .filter { it.isNotBlank() }
              .filter { it.isNotEmpty() }

            val candidateNumericalParts = candidateParts
              .drop(MODULUS_INDEX + 1)
              .map { Integer.parseInt(it) }

            val weights = parseWeightsFromCandidate(candidateNumericalParts)

            val exception: Int? = parseExceptionFromCandidate(candidateNumericalParts)

            val modulusAlgorithm: ModulusAlgorithm

            modulusAlgorithm = ModulusAlgorithm.parse(candidateParts[2])

            return WeightRow(
              modulusAlgorithm,
              weights,
              exception
            )
        }

        private fun parseExceptionFromCandidate(candidateNumericalParts: List<Int>) =
          candidateNumericalParts.getOrElse(EXCEPTION_INDEX) { null }

        private fun parseWeightsFromCandidate(candidateNumericalParts: List<Int>) =
          candidateNumericalParts.subList(0, EXCEPTION_INDEX)
    }
}
