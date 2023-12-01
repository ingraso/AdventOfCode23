package DayOne

import java.io.File

class DayOne {
    private fun getInput(): List<String> {
        return File("src/main/kotlin/DayOne/input.txt").readLines()
    }

    private fun getFirstAndLastDigits(input: String): Int {
        val firstAndLastDigits: Int
        val digits = input.filter { it.isDigit() }

        firstAndLastDigits = if (digits.length == 2) {
            digits.toInt()
        } else {
            val firstDigit = digits.first()
            val lastDigit = digits.last()
            "$firstDigit$lastDigit".toInt()
        }
        return firstAndLastDigits
    }

    fun partOne(): Int {
        println("Today is the 1st of December!")

        val inputs = getInput()
        val calibrationValues = inputs.map { getFirstAndLastDigits(it) }
        return calibrationValues.sumOf { it }
    }
}
