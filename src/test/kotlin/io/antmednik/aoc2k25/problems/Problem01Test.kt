package io.antmednik.aoc2k25.problems

import io.antmednik.io.antmednik.aoc2k25.problems.Problem01
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Problem01Test {
    @Test
    fun solvePart1() {
        assertEquals(
            3,
            Problem01().part1(50, listOf(
                "L68", "L30", "R48", "L5", "R60"
                ,"L55", "L1", "L99", "R14", "L82"
            ).asSequence())
        )
    }

    @Test
    fun solvePart2() {
        assertEquals(
            6,
            Problem01().part2(50, listOf(
                "L68", "L30", "R48", "L5", "R60"
                ,"L55", "L1", "L99", "R14", "L82"
            ).asSequence())
        )
    }
}