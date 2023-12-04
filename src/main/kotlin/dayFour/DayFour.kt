package dayFour

import java.io.File
import kotlin.math.pow
import kotlin.system.measureTimeMillis

data class Card(
    val id: Int,
    val winningNumbers: List<Int>,
    val obtainedNumbers: List<Int>,
    var amountOfWinningNumbers: Int = 0,
    var copies: Int = 1,
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

    private fun initializeCardsFromInput(inputs: List<String>): List<Card> {
        val cards = inputs.map { getCard(it) }
        cards.forEach {
            setAmountOfWinningNumbers(it)
        }
        return cards
    }

    fun partOne(inputs: List<String>): Int {
        val totalPoints = mutableListOf<Int>()
        val cards = initializeCardsFromInput(inputs)
        cards.forEach {
            totalPoints.add(
                if (it.amountOfWinningNumbers == 1) 1 else 2F.pow(it.amountOfWinningNumbers).div(2).toInt(),
            )
        }
        return totalPoints.sumOf { it }
    }

    private fun incrementCopies(card: Card, cards: List<Card>): List<Card> {
        for (i in (card.id + 1)..(card.id + card.amountOfWinningNumbers)) {
            cards[i - 1].copies += cards[card.id - 1].copies
        }
        return cards
    }

    fun partTwo(inputs: List<String>): Int {
        val cards = initializeCardsFromInput(inputs).toMutableList()
        cards.forEach { incrementCopies(it, cards) }
        return cards.sumOf { it.copies }
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
    println(dayFour.partTwo(inputs))
    println(measureTimeMillis { dayFour.partTwo(inputs) }) // 3 ms (?)
}
