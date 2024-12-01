package dec1

import java.io.File
import kotlin.math.abs

class Dec1 {
    fun getInput(): List<String> {
        return File("src/main/kotlin/dec1/input.txt").readLines()
    }

    private fun formatLocationIDsToLists(inputs: List<String>): Pair<List<Int>, List<Int>> {
        val leftSide = mutableListOf<Int>()
        val rightSide = mutableListOf<Int>()

        inputs.forEach {
            val line = it.split("   ")
            leftSide.add(line[0].toInt())
            rightSide.add(line[1].toInt())
        }

        return Pair(leftSide, rightSide)
    }

    private fun sortLocationIDs(locationIDs: Pair<List<Int>, List<Int>>): Pair<List<Int>, List<Int>> {
        val sortedLeftSide = locationIDs.first.sorted()
        val sortedRightSide = locationIDs.second.sorted()
        return Pair(sortedLeftSide, sortedRightSide)
    }

    private fun calculateTotalDifference(sortedLocationIDs: Pair<List<Int>, List<Int>>): Int {
        var totalDifference = 0
        sortedLocationIDs.first.forEachIndexed { index, locationIDLeft ->
            val locationIDRight = sortedLocationIDs.second[index]
            totalDifference += abs(locationIDLeft - locationIDRight)
        }
        return totalDifference
    }

    fun partOne(inputs: List<String>): Int {
        // Read the input and create two lists
        val locationIDs = formatLocationIDsToLists(inputs)
        // Sort each list ascending
        val sortedLocationIDs = sortLocationIDs(locationIDs)
        // Go through both lists and accumulate the difference
        return calculateTotalDifference(sortedLocationIDs)
    }

    fun partTwo(inputs: List<String>): Int {
        return 1
    }
}
fun main() {
    println("Today is the 1st of December!")
    val dec1 = Dec1()
    val inputs = dec1.getInput()
    println(dec1.partOne(inputs))
    // println(dec1.partTwo(inputs))
}
