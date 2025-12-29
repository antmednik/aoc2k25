package io.antmednik.aoc2k25.problems

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Problem06Test {

    @Test
    fun part1VectorTest() {
        assertEquals(
            4277556,
            Problem06().part1Vector(
                listOf(
                    "123 328  51 64",
                    "45 64  387 23",
                    "6 98  215 314",
                    "*   +   *   +",
                )
            )
        )
    }

    @Test
    fun part1DirectTest() {
        assertEquals(
            4277556,
            Problem06().part1Direct(
                listOf(
                    "123 328  51 64",
                    "45 64  387 23",
                    "6 98  215 314",
                    "*   +   *   +",
                )
            )
        )
    }

    @Test
    fun part2Test() {
        assertEquals(
            3263827,
            Problem06().part2(
                listOf(
                    "123 328  51 64",
                    " 45 64  387 23",
                    "  6 98  215 314",
                    "*   +   *   +",
                )
            )
        )
    }
}