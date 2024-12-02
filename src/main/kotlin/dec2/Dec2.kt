package dec2

import java.io.File

class Dec2 {
    fun getInput(): List<String> {
        return File("src/main/kotlin/dec2/input.txt").readLines()
    }

    private fun formatInput(inputs: List<String>): List<List<Int>> {
        val reports = inputs.map { it.split(",") }
        val splittedReports: List<List<String>> = reports.map { report ->
            report.flatMap { it.split(" ") }
        }

        val reportsWithInts: List<List<Int>> = splittedReports.map { report ->
            report.map { it.toInt() }
        }
        return reportsWithInts
    }

    private fun getIncreasingOrDecreasingReports(reports: List<List<Int>>): List<List<Int>> {
        return reports.filter { report ->
            val increasingLevels = report.sorted()
            val decreasingLevels = report.sortedDescending()
            report == increasingLevels || report == decreasingLevels
        }
    }

    private fun sortReports(reports: List<List<Int>>): List<List<Int>> {
        return reports.map { it.sortedDescending() }
    }

    private fun getReportsWithinThresholds(reports: List<List<Int>>): List<List<Int>> {
        return reports.filter { report ->
            val levelsButLast = report.take(report.lastIndex)
            val filteredLevels = levelsButLast.filter {
                val difference = it - report[report.indexOf(it) + 1]
                difference in 1..3
            }
            levelsButLast == filteredLevels
        }
    }

    fun partOne(inputs: List<String>): Int {
        // Need to check that each report is either increasing or decreasing
        // Then, check the thresholds
        val formattedInput = formatInput(inputs)
        val increasingOrDecreasingReports = getIncreasingOrDecreasingReports(formattedInput)
        // Sort all reports to only handle decreasing ones
        val sortedReports = sortReports(increasingOrDecreasingReports)
        val reportsWithinThreshold = getReportsWithinThresholds(sortedReports)
        return reportsWithinThreshold.size
    }

    fun partTwo(inputs: List<String>): Int {
        return 1
    }
}
fun main() {
    println("Today is the 2nd of December!")
    val dec2 = Dec2()
    val inputs = dec2.getInput()
    println(dec2.partOne(inputs))
    // println(dec1.partTwo(inputs))
}
