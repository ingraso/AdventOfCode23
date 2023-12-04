package dayThree

import java.io.File

class DayThree {
    fun getInput(): List<String> {
        return File("src/main/kotlin/DayThree/input.txt").readLines()
    }

    private fun findPartNumbersHorizontally(input: String): List<Int> {
        val partNumbers = mutableListOf<Int>()
        val regexPattern = Regex("\\d+[^\\d.]")
        regexPattern.findAll(input).forEach { matchResult ->
            partNumbers.add(matchResult.value.filter { it.isDigit() }.toInt())
        }
        return partNumbers
    }

    private fun getSymbolIndices(input: String): List<Int> {
        val symbolIndices = mutableListOf<Int>()
        Regex("[^\\d.]").findAll(input).forEach { symbolIndices.add(it.range.first) }
        return symbolIndices
    }

    private fun isSymbolNeighbour(neighbors: String): Boolean {
        return neighbors.any { !it.isLetterOrDigit() && it != '.' }
    }

    private fun findPartNumbers(inputs: List<String>): List<Int> {
        val partNumbers = mutableListOf<Int>()
        inputs.forEach { input ->
            val lineBefore = if (input == inputs.first()) {
                ""
            } else {
                inputs[inputs.indexOf(input) - 1 ]
            }
            val lineAfter = if (input == inputs.last()) {
                ""
            } else {
                inputs[inputs.indexOf(input) + 1]
            }
            val symbolIndices = getSymbolIndices(lineBefore) + getSymbolIndices(lineAfter)

            Regex("\\d+").findAll(input).forEach { matchResult ->
                val leftNeighborIndex = if (matchResult.range.first - 1 < 0) 0 else matchResult.range.first - 1
                val rightNeighborIndex = if (matchResult.range.last + 1 == input.length) input.length - 1 else matchResult.range.last + 1
                val diagonalRange = IntRange(leftNeighborIndex, rightNeighborIndex)

                if (symbolIndices.any { diagonalRange.contains(it) }) {
                    partNumbers.add(matchResult.value.toInt())
                } else {
                    val neighbors = "${input[leftNeighborIndex]}${input[rightNeighborIndex]}"
                    if (isSymbolNeighbour(neighbors)) partNumbers.add(matchResult.value.toInt())
                }
            }
        }
        return partNumbers
    }

    fun partOne(inputs: List<String>): Int {
        val partNumbers = findPartNumbers(inputs)
        return partNumbers.sumOf { it }
    }

    fun partTwo(inputs: List<String>): Int {
        val gearRatios = listOf<Int>()
        inputs.forEach { input ->
            val asterixes = Regex("\\*").findAll(input).forEach { matchResult ->
                val leftNeighborIndex = if (matchResult.range.first - 1 < 0) 0 else matchResult.range.first - 1
                val rightNeighborIndex = if (matchResult.range.last + 1 == input.length) input.length - 1 else matchResult.range.last + 1
                val diagonalRange = IntRange(leftNeighborIndex, rightNeighborIndex)
                println(matchResult.range.first)
                val adjacentPartNumbers = mutableListOf<Int>()
                // find adjacent part numbers and append them to a list
                // horizontal:
            }
        }
        // a gear is an * that is adjacent to two part numbers (numbers)
        // need to locate all *s and then create a list of adjacent part numbers
        // if the list of an * is of length 2, it is a gear
        // to find the gear ratio we need to multiply the product of the two numbers
        // then sum up all the gear ratios
        return gearRatios.sumOf { it }
    }
}

fun main() {
    println("Today is the 3rd of December!")
    val dayThree = DayThree()
    val inputs = dayThree.getInput()
    val testInput = listOf(
        "467..114..", "...*......", "..35..633.", "......#...", "617*......", ".....+.58.", "..592.....", "......755.", "...$.*....", ".664.598..",
    )
    println(dayThree.partOne(inputs))
    println(dayThree.partTwo(testInput))
}
