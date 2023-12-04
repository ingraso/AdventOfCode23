package dayFour

import java.io.File
import kotlin.math.pow

data class Card(
    val id: Int,
    val winningNumbers: List<Int>,
    val obtainedNumbers: List<Int>,
    var amountOfWinningNumbers: Int = 0,
)

class DayFour {
    fun getInput(): List<String> {
        return File("src/main/kotlin/DayFour/input.txt").readLines()
    }

    private fun getIntegersFromString(str: String): List<Int> {
        val integers = mutableListOf<Int>()
        Regex("\\d+").findAll(str).forEach { integers.add(it.value.toInt()) }
        return integers
    }

    private fun getCard(input: String): Card {
        val id = getIntegersFromString(input.substringBefore(":")).first()
        val winningNumbers = getIntegersFromString(input.substringAfter(":").substringBefore("|"))
        val obtainedNumbers = getIntegersFromString(input.substringAfter("|"))
        return Card(id, winningNumbers, obtainedNumbers)
    }

    private fun setAmountOfWinningNumbers(card: Card) {
        card.amountOfWinningNumbers = card.winningNumbers.intersect(card.obtainedNumbers.toSet()).size
    }

    fun partOne(inputs: List<String>): Int {
        val totalPoints = mutableListOf<Int>()
        val cards = inputs.map { getCard(it) }
        cards.forEach {
            setAmountOfWinningNumbers(it)
            totalPoints.add(
                if (it.amountOfWinningNumbers == 1) 1 else 2F.pow(it.amountOfWinningNumbers).div(2).toInt(),
            )
        }
        return totalPoints.sumOf { it }
    }

    fun partTwo(inputs: List<String>): Int {
        return 0
    }
}

fun main() {
    println("Today is the 4th of December!")
    val dayFour = DayFour()
    val inputs = dayFour.getInput()
    val testInput = listOf(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
    )
    println(dayFour.partOne(inputs))
    println(dayFour.partTwo(testInput))
}
