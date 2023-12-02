package dayTwo

import java.io.File

data class Game(
    val id: Int,
    val blues: MutableList<Int> = mutableListOf(),
    val greens: MutableList<Int> = mutableListOf(),
    val reds: MutableList<Int> = mutableListOf(),
)

class DayTwo {
    fun getInput(): List<String> {
        return File("src/main/kotlin/DayTwo/input.txt").readLines()
    }

    private fun mapGameToObject(game: String): Game {
        val id = game.substringAfter("Game ").substringBefore(":")
        val mappedGame = Game(id.toInt())

        val greens = Regex("\\d+ (green)").findAll(game)
        greens.forEach { matchResult -> mappedGame.greens.add(matchResult.value.substringBefore(" green").toInt()) }

        val blues = Regex("\\d+ (blue)").findAll(game)
        blues.forEach { matchResult -> mappedGame.blues.add(matchResult.value.filter { it.isDigit() }.toInt()) }

        val reds = Regex("\\d+ (red)").findAll(game)
        reds.forEach { matchResult -> mappedGame.reds.add(matchResult.value.filter { it.isDigit() }.toInt()) }

        return mappedGame
    }

    fun partOne(inputs: List<String>): Int {
        val maxGreen = 13
        val maxBlue = 14
        val maxRed = 12
        val mappedGames = inputs.map { mapGameToObject(it) }

        val validGames = mappedGames.filter { it.greens.max() <= maxGreen && it.blues.max() <= maxBlue && it.reds.max() <= maxRed }
        return validGames.sumOf { it.id }
    }
}

fun main() {
    println("Today is the 2nd of December!")
    val dayTwo = DayTwo()
    val inputs = dayTwo.getInput()
    val testInput = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
    )
    println(dayTwo.partOne(inputs))
    // println(dayOne.partTwo(inputs))
}
