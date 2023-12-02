package DayOne

import java.io.File

class DayOne {
    fun getInput(): List<String> {
        return File("src/main/kotlin/DayOne/input.txt").readLines()
    }

    private fun createRegexPattern(stringDigits: Map<String, String>): Regex {
        return stringDigits.keys.joinToString("|").toRegex()
    }

    private fun convertStringDigitsToDigits(str: String, stringDigits: Map<String, String>, regexPattern: Regex): String {
        // need to make a regex that converts from stringdigit to digit
        // regexPattern.replace(str, )
        var newString = str
        stringDigits.forEach { (stringDigit, digit) ->
            newString = newString.replace(stringDigit, digit)
        }
        return newString
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

    fun partOne(inputs: List<String>): Int {
        val calibrationValues = inputs.map { getFirstAndLastDigits(it) }
        return calibrationValues.sumOf { it }
    }

    fun partTwo(inputs: List<String>): Int {
        val stringDigits = mapOf(Pair("one", "1"), Pair("two", "2"), Pair("three", "3"), Pair("four", "4"), Pair("five", "5"), Pair("six", "6"), Pair("seven", "7"), Pair("eight", "8"), Pair("nine", "9"))
        val testInputs = listOf("two1nine", "eightwothree", "abcone2threexyz", "xtwone3four", "4nineeightseven2", "zoneight234", "7pqrstsixteen")
        val regexPattern = createRegexPattern(stringDigits)
        println(regexPattern)
        val convertedInputs = testInputs.map { convertStringDigitsToDigits(it, stringDigits, regexPattern) }
        println(convertedInputs)
        val calibrationValues = convertedInputs.map { getFirstAndLastDigits(it) }
        println(calibrationValues)
        return calibrationValues.sumOf { it }
    }
}
fun main() {
    println("Today is the 1st of December!")
    val dayOne = DayOne()
    val inputs = dayOne.getInput()
    println(dayOne.partOne(inputs))
    println(dayOne.partTwo(inputs))
}
