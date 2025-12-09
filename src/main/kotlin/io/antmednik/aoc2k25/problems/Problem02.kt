package io.antmednik.aoc2k25.problems

import io.antmednik.aoc2k25.common.InputFileSequence
import kotlin.use

/**
 * --- Day 2: Gift Shop ---
 * You get inside and take the elevator to its only other stop: the gift shop. "Thank you for visiting the North Pole!"
 * gleefully exclaims a nearby sign. You aren't sure who is even allowed to visit the North Pole, but you know you
 * can access the lobby through here, and from there you can access the rest of the North Pole base.
 *
 * As you make your way through the surprisingly extensive selection, one of the clerks recognizes you and asks for
 * your help.
 *
 * As it turns out, one of the younger Elves was playing on a gift shop computer and managed to add a whole bunch of
 * invalid product IDs to their gift shop database! Surely, it would be no trouble for you to identify the invalid
 * product IDs for them, right?
 *
 * They've even checked most of the product ID ranges already; they only have a few product ID ranges
 * (your puzzle input) that you'll need to check. For example:
 *
 * 11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
 * 1698522-1698528,446443-446449,38593856-38593862,565653-565659,
 * 824824821-824824827,2121212118-2121212124
 * (The ID ranges are wrapped here for legibility; in your input, they appear on a single long line.)
 *
 * The ranges are separated by commas (,); each range gives its first ID and last ID separated by a dash (-).
 *
 * Since the young Elf was just doing silly patterns, you can find the invalid IDs by looking for any ID which is
 * made only of some sequence of digits repeated twice. So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice)
 * would all be invalid IDs.
 *
 * None of the numbers have leading zeroes; 0101 isn't an ID at all. (101 is a valid ID that you would ignore.)
 *
 * Your job is to find all of the invalid IDs that appear in the given ranges. In the above example:
 *
 * 11-22 has two invalid IDs, 11 and 22.
 * 95-115 has one invalid ID, 99.
 * 998-1012 has one invalid ID, 1010.
 * 1188511880-1188511890 has one invalid ID, 1188511885.
 * 222220-222224 has one invalid ID, 222222.
 * 1698522-1698528 contains no invalid IDs.
 * 446443-446449 has one invalid ID, 446446.
 * 38593856-38593862 has one invalid ID, 38593859.
 * The rest of the ranges contain no invalid IDs.
 * Adding up all the invalid IDs in this example produces 1227775554.
 *
 * What do you get if you add up all of the invalid IDs?
 */
class Problem02 {
    fun solvePart1(): Long {
        return InputFileSequence("problem02.txt").use { ife ->
            ife.first().split(',').sumOf {
                val rawRange = it.split("-")
                sumInvalidIds(rawRange[0], rawRange[1])
            }
        }
    }

    internal fun sumInvalidIds(startId: String, endId: String): Long {
        val isStartIdOdd = startId.length % 2 == 1
        if (startId.length == endId.length && isStartIdOdd
            || endId == "10") {
            return 0
        }
        var invalidIdsSum = 0L
        var idHalfLen: Int
        var startInvalidIdHalf: Long
        if (isStartIdOdd) {
            idHalfLen = (startId.length + 1) / 2
            startInvalidIdHalf = ("1" + "0".repeat(idHalfLen - 1)).toLong()
        } else {
            idHalfLen = startId.length / 2
            val a = startId.take(idHalfLen).toLong()
            val b = startId.takeLast(idHalfLen).toLong()
            startInvalidIdHalf = if (a >= b) a else a + 1   // idHalfLen overflow is handled
        }
        var maxIdHalfLen: Int
        var endInvalidIdHalf: Long
        if (endId.length % 2 == 1) {
            maxIdHalfLen = (endId.length - 1) / 2
            endInvalidIdHalf = "9".repeat(maxIdHalfLen).toLong()
        } else {
            maxIdHalfLen = endId.length / 2
            val a = endId.take(maxIdHalfLen).toLong()
            val b = endId.takeLast(maxIdHalfLen).toLong()
            endInvalidIdHalf = if (a <= b) a else a - 1
            // handle underflow
            if (endInvalidIdHalf.toString().length != maxIdHalfLen) {
                maxIdHalfLen--
                endInvalidIdHalf = "9".repeat(maxIdHalfLen).toLong()
            }
        }
        var nextStartInvalidIdHalf = ("1" + "0".repeat(idHalfLen)).toLong()
        while (idHalfLen < maxIdHalfLen) {
            while (startInvalidIdHalf < nextStartInvalidIdHalf) {
                invalidIdsSum += getFull(startInvalidIdHalf, idHalfLen)
                startInvalidIdHalf++
            }

            startInvalidIdHalf = nextStartInvalidIdHalf
            idHalfLen++
            nextStartInvalidIdHalf = ("1" + "0".repeat(idHalfLen)).toLong()
        }
        while (startInvalidIdHalf <= endInvalidIdHalf) {
            invalidIdsSum += getFull(startInvalidIdHalf, idHalfLen)
            startInvalidIdHalf++
        }
        return invalidIdsSum
    }

    // 123 -> 123123
    private fun getFull(half: Long, len: Int): Long {
        var result: Long = half
        repeat(len) {
            result *= 10
        }
        return result + half
    }
}