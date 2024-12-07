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

    private fun reorderUnsafeUpdates(updates: List<List<String>>, rules: List<List<String>>): List<List<String>> {
        // Go through each unsafe update
        // start at the last index of the update
        // check if the element should be before any of the other elements in the update
        // if yes, move these elements after the current one
        // then, start at the last index again, and do the same with this element

        // when the last element is safe, move to the previous index and repeat the process
        // do this all the way through to the first element in the list

        val firstElementsInRules = rules.map { it[0] }
        val secondElementsInRules = rules.map { it[1] }

        val reorderedUpdates = updates.map { update ->
            val reorderedUpdate = mutableListOf<String>()
            var editedUpdate = update.toMutableList()

            while (update.size != reorderedUpdate.size) {
                val currentElement = editedUpdate.last()

                val indices = firstElementsInRules.mapIndexedNotNull { index, el -> index.takeIf { el == currentElement } }
                val rulesForElementInUpdate = indices.map { secondElementsInRules[it] }.intersect(editedUpdate)
                if (rulesForElementInUpdate.isEmpty()) {
                    // No rules for the current element are broken
                    reorderedUpdate.add(currentElement)
                    editedUpdate = editedUpdate.dropLast(1).toMutableList()
                } else {
                    // Rules for the current element are broken, and we need to handle this
                    editedUpdate = ((editedUpdate - rulesForElementInUpdate) + rulesForElementInUpdate).toMutableList()
                }
            }
            reorderedUpdate.toList().reversed()
        }
        return reorderedUpdates
    }

    fun partTwo(inputs: List<String>): Int {
        val indexOfNewLine = inputs.indexOf("")
        val pageOrderingRules = inputs.take(indexOfNewLine)
        val formattedPageOrderingRules = formatPageOrderingRules(pageOrderingRules)

        val updates = inputs.drop(indexOfNewLine + 1)
        val formattedUpdates = formatUpdates(updates)
        val safeUpdates = getSafeUpdates(formattedUpdates, formattedPageOrderingRules)
        val unsafeUpdates = formattedUpdates - safeUpdates
        val reorderedUnsafeUpdates = reorderUnsafeUpdates(unsafeUpdates, formattedPageOrderingRules)
        return getMiddleElements(reorderedUnsafeUpdates).sum()
    }
}
fun main() {
    println("Today is the 5th of December!")
    val dec5 = Dec5()
    val inputs = dec5.getInput()
    // println(dec5.partOne(inputs))
    println(dec5.partTwo(inputs))
}
