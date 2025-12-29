package io.antmednik.aoc2k25.problems

import io.antmednik.aoc2k25.common.InputFileSequence
import jdk.incubator.vector.LongVector
import jdk.incubator.vector.VectorOperators.ADD
import jdk.incubator.vector.VectorOperators.MUL
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.use

/**
 * --- Day 6: Trash Compactor ---
 * After helping the Elves in the kitchen, you were taking a break and helping them re-enact a movie scene when you
 * over-enthusiastically jumped into the garbage chute!
 *
 * A brief fall later, you find yourself in a garbage smasher. Unfortunately, the door's been magnetically sealed.
 *
 * As you try to find a way out, you are approached by a family of cephalopods! They're pretty sure they can get the
 * door open, but it will take some time. While you wait, they're curious if you can help the youngest cephalopod
 * with her math homework.
 *
 * Cephalopod math doesn't look that different from normal math. The math worksheet (your puzzle input) consists of
 * a list of problems; each problem has a group of numbers that need to be either added (+) or multiplied (*) together.
 *
 * However, the problems are arranged a little strangely; they seem to be presented next to each other in a very
 * long horizontal list. For example:
 *
 * 123 328  51 64
 *  45 64  387 23
 *   6 98  215 314
 * *   +   *   +
 * Each problem's numbers are arranged vertically; at the bottom of the problem is the symbol for the operation
 * that needs to be performed. Problems are separated by a full column of only spaces. The left/right alignment
 * of numbers within each problem can be ignored.
 *
 * So, this worksheet contains four problems:
 *
 * 123 * 45 * 6 = 33210
 * 328 + 64 + 98 = 490
 * 51 * 387 * 215 = 4243455
 * 64 + 23 + 314 = 401
 * To check their work, cephalopod students are given the grand total of adding together all of the answers to the
 * individual problems. In this worksheet, the grand total is 33210 + 490 + 4243455 + 401 = 4277556.
 *
 * Of course, the actual worksheet is much wider. You'll need to make sure to unroll it completely so that you can
 * read the problems clearly.
 *
 * Solve the problems on the math worksheet. What is the grand total found by adding together all of the answers to
 * the individual problems?
 *
 * --- Part Two ---
 * The big cephalopods come back to check on how things are going. When they see that your grand total doesn't
 * match the one expected by the worksheet, they realize they forgot to explain how to read cephalopod math.
 *
 * Cephalopod math is written right-to-left in columns. Each number is given in its own column, with the most
 * significant digit at the top and the least significant digit at the bottom. (Problems are still separated
 * with a column consisting only of spaces, and the symbol at the bottom of the problem is still the operator to use.)
 *
 * Here's the example worksheet again:
 *
 * 123 328  51 64
 *  45 64  387 23
 *   6 98  215 314
 * *   +   *   +
 * Reading the problems right-to-left one column at a time, the problems are now quite different:
 *
 * The rightmost problem is 4 + 431 + 623 = 1058
 * The second problem from the right is 175 * 581 * 32 = 3253600
 * The third problem from the right is 8 + 248 + 369 = 625
 * Finally, the leftmost problem is 356 * 24 * 1 = 8544
 * Now, the grand total is 1058 + 3253600 + 625 + 8544 = 3263827.
 *
 * Solve the problems on the math worksheet again. What is the grand total found by adding together all of the
 * answers to the individual problems?
 */
class Problem06 {
    fun part1(): Long {
        val input = readInput()
        return part1Direct(input)
    }

    fun part2(): Long {
        val input = readInput()
        return part2(input)
    }

    internal fun part1Direct(rawInput: List<String>): Long {
        val start = System.nanoTime()
        var result = 0L
        val rawOperands = mutableListOf<List<String>>()
        for (i in 0..<rawInput.lastIndex) {
            rawOperands.add(rawInput[i].split(SPLIT_REGEX))
        }
        val ops = rawInput[rawInput.lastIndex].split(SPLIT_REGEX)
        for (problemIdx in ops.indices) {
            if (ops[problemIdx] == SUM_OP) {
                for (rawOperand in rawOperands) {
                    result += rawOperand[problemIdx].toLong()
                }
            } else {
                var mul = 1L
                for (rawOperand in rawOperands) {
                    mul *= rawOperand[problemIdx].toLong()
                }
                result += mul
            }
        }
        val end = System.nanoTime()
        println("took: ${(end-start).nanoseconds}")
        return result
    }

    private fun readInput(): List<String> {
        val input = mutableListOf<String>()
        InputFileSequence("problem06.txt").use { ife ->
            ife.forEach {
                input.add(it)
            }
        }
        return input
    }

    // for micro-optimizations
    internal fun part1Vector(rawInput: List<String>): Long {
        val start = System.nanoTime()
        val ops = rawInput[rawInput.lastIndex].split(SPLIT_REGEX)
        val rawOperands = mutableListOf<List<String>>()
        for (i in 0..<rawInput.lastIndex) {
            rawOperands.add(rawInput[i].split(SPLIT_REGEX))
        }
        val sumOperands = LongArray(rawOperands.size)
        val mulOperands = LongArray(rawOperands.size)
        var result = 0L
        for (problemIdx in ops.indices) {
            if (ops[problemIdx] == SUM_OP) {
                rawOperands.forEachIndexed { index, operand ->
                    sumOperands[index] = operand[problemIdx].toLong()
                }
                val vector = LongVector.fromArray(LongVector.SPECIES_256, sumOperands, 0)
                result += vector.reduceLanesToLong(ADD)
            } else {
                rawOperands.forEachIndexed { index, operand ->
                    mulOperands[index] = operand[problemIdx].toLong()
                }
                val vector = LongVector.fromArray(LongVector.SPECIES_256, mulOperands, 0)
                result += vector.reduceLanesToLong(MUL)
            }
        }
        val end = System.nanoTime()
        println("took: ${(end-start).nanoseconds}")
        return result
    }

    internal fun part2(input: List<String>): Long {
        var columnIdx = 0
        val maxColumnIdx = input.maxOfOrNull { it.lastIndex }!!
        val ops = input[input.lastIndex]
        val numBuilder = StringBuilder()
        var result = 0L
        var isSum = true
        var problemResult = 0L
        while (columnIdx <= maxColumnIdx) {
            if (columnIdx <= ops.lastIndex && ops[columnIdx] != ' ') {
                if (ops[columnIdx] == '*') {
                    isSum = false
                    problemResult = 1
                } else {
                    isSum = true
                    problemResult = 0
                }
            }

            numBuilder.clear()
            for (rowIdx in 0..<input.lastIndex)
            {
                val d = if (columnIdx <= input[rowIdx].lastIndex) input[rowIdx][columnIdx] else ' '
                if (d != ' ') {
                    numBuilder.append(d)
                }
            }
            if (numBuilder.isNotEmpty()) {
                if (isSum) {
                    problemResult += numBuilder.toString().toLong()
                } else {
                    problemResult *= numBuilder.toString().toLong()
                }
            } else {
                result += problemResult
            }
            columnIdx++
        }
        result += problemResult
        return result
    }

    private fun transformCephalopodNumbers(numbers: List<String>): LongArray {
        val result = LongArray(numbers.size)

        return result
    }

    companion object {
        const val SUM_OP = "+"
        val SPLIT_REGEX = Regex("\\s+")
    }
}