package dec5

import java.io.File

class Dec5 {
    fun getInput(): List<String> {
        return File("src/main/kotlin/dec5/input.txt").readLines()
    }

    private fun formatPageOrderingRules(pageOrderingRules: List<String>): List<List<String>> {
        return pageOrderingRules
            .map { it.split(",") }
            .map { rule ->
                rule.flatMap {
                    it.split(("|"))
                }
            }
    }

    private fun formatUpdates(updates: List<String>): List<List<String>> {
        return updates.map { it.split(",") }
    }

    private fun getSafeUpdates(updates: List<List<String>>, rules: List<List<String>>): List<List<String>> {
        val firstElementsInRules = rules.map { it[0] }
        val secondElementsInRules = rules.map { it[1] }

        val safeUpdates = updates.filter { update ->
            var isSafe = true
            update.forEach { element ->
                val indices = secondElementsInRules.mapIndexedNotNull { index, el -> index.takeIf { el == element } }
                val rulesForElement = indices.map { firstElementsInRules[it] }
                val remainingElementsInUpdate = update.drop(update.indexOf(element) + 1)
                if (remainingElementsInUpdate.intersect(rulesForElement).isNotEmpty()) isSafe = false
            }
            isSafe
        }
        return safeUpdates
    }

    private fun getMiddleElements(updates: List<List<String>>): List<Int> {
        return updates.map { it[it.size / 2] }
            .map { it.toInt() }
    }

    fun partOne(inputs: List<String>): Int {
        val indexOfNewLine = inputs.indexOf("")
        val pageOrderingRules = inputs.take(indexOfNewLine)
        val updates = inputs.drop(indexOfNewLine + 1)
        val formattedPageOrderingRules = formatPageOrderingRules(pageOrderingRules)
        val formattedUpdates = formatUpdates(updates)
        val safeUpdates = getSafeUpdates(formattedUpdates, formattedPageOrderingRules)
        return getMiddleElements(safeUpdates).sum()
    }

    fun partTwo(inputs: List<String>): Int {
        return 1
    }
}
fun main() {
    println("Today is the 5th of December!")
    val dec5 = Dec5()
    val inputs = dec5.getInput()
    println(dec5.partOne(inputs))
    println(dec5.partTwo(inputs))
}
