package io.antmednik.aoc2k25.problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Problem05Test {
    @Test
    fun solvePart1() {
        assertEquals(
            3,
            Problem05().part1(
                listOf(
                    Problem05.Range(3, 5),
                    Problem05.Range(10, 14),
                    Problem05.Range(16, 20),
                    Problem05.Range(12, 18),

                ),
                listOf(1, 5, 8, 11, 17, 32))
        )
    }
}