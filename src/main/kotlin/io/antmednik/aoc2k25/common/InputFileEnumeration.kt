package io.antmednik.aoc2k25.common

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.AutoCloseable
import java.util.*

class InputFileEnumeration(resourceFileName: String) : Enumeration<String>, AutoCloseable {
    private val reader = BufferedReader(
        InputStreamReader(
            Objects.requireNonNull(
                javaClass.getResourceAsStream("/$resourceFileName")
            )
        )
    )
    private var nextLine: String? = null

    override fun hasMoreElements(): Boolean {
        try {
            nextLine = reader.readLine()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return nextLine != null
    }

    override fun nextElement(): String {
        return nextLine!!
    }

    override fun close() {
        try {
            reader.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}
