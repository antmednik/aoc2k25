package io.antmednik.aoc2k25.problems

import io.antmednik.aoc2k25.common.InputFileSequence
import java.util.NavigableSet
import java.util.TreeSet
import kotlin.sequences.forEach
import kotlin.use

/**
 * --- Day 5: Cafeteria ---
 * As the forklifts break through the wall, the Elves are delighted to discover that there was a cafeteria on the
 * other side after all.
 *
 * You can hear a commotion coming from the kitchen. "At this rate, we won't have any time left to put the wreaths
 * up in the dining hall!" Resolute in your quest, you investigate.
 *
 * "If only we hadn't switched to the new inventory management system right before Christmas!" another Elf exclaims.
 * You ask what's going on.
 *
 * The Elves in the kitchen explain the situation: because of their complicated new inventory management system,
 * they can't figure out which of their ingredients are fresh and which are spoiled. When you ask how it works,
 * they give you a copy of their database (your puzzle input).
 *
 * The database operates on ingredient IDs. It consists of a list of fresh ingredient ID ranges, a blank line,
 * and a list of available ingredient IDs. For example:
 *
 * 3-5
 * 10-14
 * 16-20
 * 12-18
 *
 * 1
 * 5
 * 8
 * 11
 * 17
 * 32
 * The fresh ID ranges are inclusive: the range 3-5 means that ingredient IDs 3, 4, and 5 are all fresh.
 * The ranges can also overlap; an ingredient ID is fresh if it is in any range.
 *
 * The Elves are trying to determine which of the available ingredient IDs are fresh. In this example,
 * this is done as follows:
 *
 * Ingredient ID 1 is spoiled because it does not fall into any range.
 * Ingredient ID 5 is fresh because it falls into range 3-5.
 * Ingredient ID 8 is spoiled.
 * Ingredient ID 11 is fresh because it falls into range 10-14.
 * Ingredient ID 17 is fresh because it falls into range 16-20 as well as range 12-18.
 * Ingredient ID 32 is spoiled.
 * So, in this example, 3 of the available ingredient IDs are fresh.
 *
 * Process the database file from the new inventory management system.
 * How many of the available ingredient IDs are fresh?
 */
class Problem05 {

    fun part1(): Int {
        val ranges = mutableListOf<Range>()
        val values = mutableListOf<Long>()
        InputFileSequence("problem05.txt").use { ife ->
            var readingRanges = true
            ife.forEach {
                if (it.isEmpty()) {
                    readingRanges = false
                } else {
                    if (readingRanges) {
                        val splitted = it.split('-')
                        ranges.add(Range(splitted[0].toLong(), splitted[1].toLong()))
                    } else {
                        values.add(it.toLong())
                    }
                }
            }
        }
        return part1(ranges, values)
    }

    internal fun part1(ranges: List<Range>, values: List<Long>): Int {
        val r = Ranges()
        ranges.forEach { r.add(it) }
        r.validate()
        var freshCount = 0
        for (v in values) {
            if (r.isInRange(v)) {
                freshCount++
            }
        }
        return freshCount
    }

    class Ranges {
        private val ranges: NavigableSet<Range> = TreeSet()

        fun isInRange(value: Long): Boolean {
            val left = ranges.floor(Range(value, -1))
            return left != null && left.end >= value
        }

        fun validate() {
            var a: Range? = null
            var b: Range? = null
            var c: Range? = null
            for (v in ranges) {
                a = b
                b = c
                c = v

                if (b != null) {
                    if (a != null) {
                        require(a.end + 1 < b.start)
                    }
                    require(b.end + 1 < c.start)
                }
            }
        }

        fun add(newRange: Range) {
            if (ranges.isEmpty()) {
                ranges.add(newRange)
                return
            }
            val left = ranges.floor(newRange)
            val right = ranges.ceiling(newRange)
            if (left == null) {
                if (newRange.end < right!!.start) {
                    ranges.add(newRange)
                } else {
                    ranges.remove(right)
                    ranges.add(Range(newRange.start, right.end))
                }
                return
            }
            if (right == null) {
                if (left.end < newRange.start) {
                    ranges.add(newRange)
                } else {
                    ranges.remove(left)
                    ranges.add(Range(left.start, newRange.end))
                }
                return
            }
            /**
             * !___!
             * !______!
             */
            if (left === right) {
                if (newRange.end > left.end) {
                    ranges.remove(left)
                    add(newRange)
                }
                return
            }
            if (newRange.start <= left.end + 1) {
                if (newRange.end <= left.end) {
                    /**
                     * !______!
                     *   !__!
                     */
                    return
                }
                var newEnd = newRange.end
                val within = ranges.subSet(newRange, false, Range(newRange.end + 1, -1), true)
                if (within.isNotEmpty()) {
                    val last = within.last
                    if (newEnd < last.end) {
                        newEnd = last.end
                    }
                    ranges.removeAll(within)
                }
                ranges.remove(left)
                add(Range(left.start, newEnd))
            } else {
                var newEnd = newRange.end
                val within = ranges.subSet(Range(newRange.start - 1, -1), true, Range(newRange.end + 1, -1), true)
                if (within.isNotEmpty()) {
                    val last = within.last
                    if (newEnd < last.end) {
                        newEnd = last.end
                    }
                    ranges.removeAll(within)
                    add(Range(newRange.start, newEnd))
                }
                else {
                    ranges.add(Range(newRange.start, newEnd))
                }
            }
        }
    }

    data class Range(
        val start: Long,
        val end: Long
    ): Comparable<Range> {
        init {
            require(start <= end || end == -1L) { "start: $start, end: $end" }
        }

        override fun compareTo(other: Range): Int = start.compareTo(other.start)
    }
}