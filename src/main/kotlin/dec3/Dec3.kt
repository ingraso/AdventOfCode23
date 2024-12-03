package dec3

import java.io.File

class Dec3 {
    fun getInput(): String {
        return File("src/main/kotlin/dec3/input.txt").readLines().joinToString()
    }

    private fun getInstructions(inputs: String): List<List<String>> {
        val instructions = mutableListOf<String>()
        val regex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
        val matches = regex.findAll(inputs)
        matches.forEach { instructions.add(it.value) }

        return instructions
            .map { it.replace("mul(", "") }
            .map { it.replace(")", "") }
            .map { it.split(",") }
    }

    private fun calculateProducts(instructions: List<List<String>>): List<Int> {
        return instructions.map {
            it[0].toInt() * it[1].toInt()
        }
    }

    fun partOne(inputs: String): Int {
        // Use regex to extract the instructions on the format 'mul(X,Y)'
        // Multiply X and Y for all instructions
        // Sum up all products
        val instructions = getInstructions(inputs)
        val products = calculateProducts(instructions)
        return products.sum()
    }

    private fun getInstructionsIncludingConditionals(inputs: String): List<List<String>> {
        val instructionsWithMul = mutableListOf<String>()
        val regex = "mul\\(\\d{1,3},\\d{1,3}\\)|(don't|do)".toRegex()
        val matches = regex.findAll(inputs)
        matches.forEach { instructionsWithMul.add(it.value) }

        val instructionsWithConditions = instructionsWithMul
            .map { it.replace("mul(", "") }
            .map { it.replace(")", "") }
            .map { it.split(",") }

        // go through all instructions and store the conditional state
        // drop instructions if conditional state is don't, keep if it is do?
        var conditionalState = "do"
        val instructions = mutableListOf<List<String>>()
        instructionsWithConditions.forEach {
            if (it[0] == "do") {
                conditionalState = "do"
            } else if (it[0] == "don't") {
                conditionalState = "don't"
            } else {
                if (conditionalState == "do") instructions.add(it)
            }
        }
        return instructions
    }

    fun partTwo(inputs: String): Int {
        val instructions = getInstructionsIncludingConditionals(inputs).toList()
        val products = calculateProducts(instructions)
        return products.sum()
    }
}
fun main() {
    println("Today is the 3rd of December!")
    val dec3 = Dec3()
    val inputs = dec3.getInput()
    // NB: different test inputs for parts 1 and 2
    // println(dec3.partOne(inputs))
    println(dec3.partTwo(inputs))
}
