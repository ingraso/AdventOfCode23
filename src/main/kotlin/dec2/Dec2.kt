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

    private fun getSafeReports(reports: List<List<Int>>): List<List<Int>> {
        val increasingOrDecreasingReports = getIncreasingOrDecreasingReports(reports)
        // Sort all reports to only handle decreasing ones
        val sortedReports = sortReports(increasingOrDecreasingReports)
        return getReportsWithinThresholds(sortedReports)
    }

    fun partOne(inputs: List<String>): Int {
        // Need to check that each report is either increasing or decreasing
        // Then, check the thresholds
        val formattedInput = formatInput(inputs)
        return getSafeReports(formattedInput).size
    }

    private fun getToleratedReports(reports: List<List<Int>>): List<List<Int>> {
        // for each report, remove one element and check if the report is safe
        val toleratedReports = mutableListOf<List<Int>>()

        reports.forEach { report ->
            var isTolerable = false
            report.forEachIndexed { index, _ ->
                val reportWithoutLevel = report.toMutableList()
                reportWithoutLevel.removeAt(index)
                if (getSafeReports(listOf(reportWithoutLevel)).isNotEmpty()) {
                    isTolerable = true
                }
            }
            if (isTolerable) {
                toleratedReports.add(report)
            }
        }
        return toleratedReports
    }

    fun partTwo(inputs: List<String>): Int {
        // Of all unsafe reports, we need to check who can be re-added based on the toleration of one bad level
        // We will force through the removal of each of the levels in the unsafe reports, adding the ones that end up being safe

        val formattedInput = formatInput(inputs)
        val safeReports = getSafeReports(formattedInput)
        val unsafeReports = formattedInput.filter { report ->
            !safeReports.contains(report) && !safeReports.contains(report.sortedDescending())
        }

        val toleratedReports = getToleratedReports(unsafeReports)
        val totalSafeReports = safeReports + toleratedReports
        return totalSafeReports.size
    }
}
fun main() {
    println("Today is the 2nd of December!")
    val dec2 = Dec2()
    val inputs = dec2.getInput()
    // println(dec2.partOne(inputs))
    println(dec2.partTwo(inputs))
}
