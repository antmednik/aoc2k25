package io.antmednik.aoc2k25.problems

import io.antmednik.io.antmednik.aoc2k25.problems.Problem01
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class Problem02Test {
    @ParameterizedTest
    @ValueSource(strings = [
        "11,22,33",
        "95,115,99",
        "998,1012,1010",
        "1188511880,1188511890,1188511885",
        "222220,222224,222222",
        "1698522,1698528,0",
        "446443,446449,446446",
        "38593856,38593862,38593859"
    ])
    fun solvePart1(range: String) {
        val splitted = range.split(',')
        val start = splitted[0]
        val end = splitted[1]
        val expected = splitted[2]
        assertEquals(
            expected.toLong(),
            Problem02().sumInvalidIds(start, end)
        )
    }
}