package io.antmednik.aoc2k25.common

import io.antmednik.aoc2k25.common.InputFileEnumeration

class InputFileSequence(resourceFileName: String): Sequence<String>, AutoCloseable {
    private val enumeration = InputFileEnumeration(resourceFileName)

    override fun iterator(): Iterator<String> {
        return enumeration.asIterator()
    }

    override fun close() {
        enumeration.close()
    }
}