package io.antmednik.aoc2k25.problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class Problem03Test {
    @ParameterizedTest
    @ValueSource(strings = [
        "987654321111111,98",
        "811111111111119,89",
        "234234234234278,78",
        "818181911112111,92",
    ])
    fun findMaxInBankTest(data: String) {
        val splitted = data.split(',')
        val bank = splitted[0]
        val expected = splitted[1].toInt()
        assertEquals(
            expected,
            Problem03().findMaxInBank(bank)
        )
    }
}