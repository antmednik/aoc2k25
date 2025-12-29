package io.antmednik.aoc2k25.problems

import io.antmednik.aoc2k25.common.InputFileSequence
import jdk.incubator.vector.LongVector
import jdk.incubator.vector.VectorOperators.ADD
import jdk.incubator.vector.VectorOperators.MUL
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Instant
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
 */
class Problem06 {
    fun part1(): Long {
        val input = readInput()
        return part1Vector(input)
    }

    // 6503327062445
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

    // ToDo: incorrect
    internal fun part1Vector(rawInput: List<String>): Long {
        val start = System.nanoTime()
        val ops = rawInput[rawInput.lastIndex].split(SPLIT_REGEX)
        val sumsCount = ops.count { it == SUM_OP }
        val rawOperands = mutableListOf<List<String>>()
        for (i in 0..<rawInput.lastIndex) {
            rawOperands.add(rawInput[i].split(SPLIT_REGEX))
        }
        val sumOperands = LongArray(rawOperands.size * sumsCount + (ops.size - sumsCount)) { 0 }
        val mulOperands = LongArray(rawOperands.size + 1) { 1 }
        var sumOperandIdx = 0
        for (problemIdx in ops.indices) {
            if (ops[problemIdx] == SUM_OP) {
                rawOperands.forEach { operand ->
                    sumOperands[sumOperandIdx++] = operand[problemIdx].toLong()
                }
            } else {
                rawOperands.forEachIndexed { index, operand ->
                    mulOperands[index] = operand[problemIdx].toLong()
                }
                val vector = LongVector.fromArray(LongVector.SPECIES_256, mulOperands, 0)
                val result = vector.reduceLanesToLong(MUL)
                sumOperands[sumOperandIdx++] = result
            }
        }
        val vector = LongVector.fromArray(LongVector.SPECIES_PREFERRED, sumOperands, 0)
        val result = vector.reduceLanesToLong(ADD)
        val end = System.nanoTime()
        println("took: ${(end-start).nanoseconds}")
        return result
    }

    companion object {
        const val SUM_OP = "+"
        val SPLIT_REGEX = Regex("\\s+")
    }
}