package io.antmednik.aoc2k25.problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Problem04Test {
    @Test
    fun solvePart1() {
        assertEquals(
            13,
            Problem04().solvePart1(
                listOf(
                    "..@@.@@@@.".toCharArray(),
                    "@@@.@.@.@@".toCharArray(),
                    "@@@@@.@.@@".toCharArray(),
                    "@.@@@@..@.".toCharArray(),
                    "@@.@@@@.@@".toCharArray(),
                    ".@@@@@@@.@".toCharArray(),
                    ".@.@.@.@@@".toCharArray(),
                    "@.@@@.@@@@".toCharArray(),
                    ".@@@@@@@@.".toCharArray(),
                    "@.@.@@@.@.".toCharArray()
                )
            )
        )
    }

    @Test
    fun solvePart2() {
        assertEquals(
            43,
            Problem04().solvePart2(
                listOf(
                    "..@@.@@@@.".toCharArray(),
                    "@@@.@.@.@@".toCharArray(),
                    "@@@@@.@.@@".toCharArray(),
                    "@.@@@@..@.".toCharArray(),
                    "@@.@@@@.@@".toCharArray(),
                    ".@@@@@@@.@".toCharArray(),
                    ".@.@.@.@@@".toCharArray(),
                    "@.@@@.@@@@".toCharArray(),
                    ".@@@@@@@@.".toCharArray(),
                    "@.@.@@@.@.".toCharArray()
                )
            )
        )
    }
}