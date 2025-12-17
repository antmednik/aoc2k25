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

    @ParameterizedTest
    @ValueSource(strings = [
        "987654321111111,987654321111",
        "811111111111119,811111111119",
        "234234234234278,434234234278",
        "818181911112111,888911112111",
    ])
    fun finMax12InBankNaiveTest(data: String) {
        val splitted = data.split(',')
        val bank = splitted[0]
        val expected = splitted[1].toLong()
        assertEquals(
            expected,
            Problem03().finMax12InBankNaive(bank)
        )
    }
}