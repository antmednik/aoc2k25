package io.antmednik.aoc2k25.problems

import io.antmednik.aoc2k25.common.InputFileSequence
import kotlin.use

/**
 * --- Day 4: Printing Department ---
 * You ride the escalator down to the printing department. They're clearly getting ready for Christmas;
 * they have lots of large rolls of paper everywhere, and there's even a massive printer in the corner
 * (to handle the really big print jobs).
 *
 * Decorating here will be easy: they can make their own decorations. What you really need is a way to get further
 * into the North Pole base while the elevators are offline.
 *
 * "Actually, maybe we can help with that," one of the Elves replies when you ask for help. "We're pretty sure there's
 * a cafeteria on the other side of the back wall. If we could break through the wall, you'd be able to keep moving.
 * It's too bad all of our forklifts are so busy moving those big rolls of paper around."
 *
 * If you can optimize the work the forklifts are doing, maybe they would have time to spare to break through the wall.
 *
 * The rolls of paper (@) are arranged on a large grid; the Elves even have a helpful diagram (your puzzle input)
 * indicating where everything is located.
 *
 * For example:
 *
 * ..@@.@@@@.
 * @@@.@.@.@@
 * @@@@@.@.@@
 * @.@@@@..@.
 * @@.@@@@.@@
 * .@@@@@@@.@
 * .@.@.@.@@@
 * @.@@@.@@@@
 * .@@@@@@@@.
 * @.@.@@@.@.
 * The forklifts can only access a roll of paper if there are fewer than four rolls of paper in the eight adjacent
 * positions. If you can figure out which rolls of paper the forklifts can access, they'll spend less time looking
 * and more time breaking down the wall to the cafeteria.
 *
 * In this example, there are 13 rolls of paper that can be accessed by a forklift (marked with x):
 *
 * ..xx.xx@x.
 * x@@.@.@.@@
 * @@@@@.x.@@
 * @.@@@@..@.
 * x@.@@@@.@x
 * .@@@@@@@.@
 * .@.@.@.@@@
 * x.@@@.@@@@
 * .@@@@@@@@.
 * x.x.@@@.x.
 *
 * Consider your complete diagram of the paper roll locations. How many rolls of paper can be accessed by a forklift?
 *
 * --- Part Two ---
 * Now, the Elves just need help accessing as much of the paper as they can.
 *
 * Once a roll of paper can be accessed by a forklift, it can be removed. Once a roll of paper is removed,
 * the forklifts might be able to access more rolls of paper, which they might also be able to remove.
 * How many total rolls of paper could the Elves remove if they keep repeating this process?
 *
 * Starting with the same example as above, here is one way you could remove as many rolls of paper as possible,
 * using highlighted @ to indicate that a roll of paper is about to be removed, and using x to
 * indicate that a roll of paper was just removed:
 *
 * Initial state:
 * ..@@.@@@@.
 * @@@.@.@.@@
 * @@@@@.@.@@
 * @.@@@@..@.
 * @@.@@@@.@@
 * .@@@@@@@.@
 * .@.@.@.@@@
 * @.@@@.@@@@
 * .@@@@@@@@.
 * @.@.@@@.@.
 *
 * Remove 13 rolls of paper:
 * ..xx.xx@x.
 * x@@.@.@.@@
 * @@@@@.x.@@
 * @.@@@@..@.
 * x@.@@@@.@x
 * .@@@@@@@.@
 * .@.@.@.@@@
 * x.@@@.@@@@
 * .@@@@@@@@.
 * x.x.@@@.x.
 *
 * Remove 12 rolls of paper:
 * .......x..
 * .@@.x.x.@x
 * x@@@@...@@
 * x.@@@@..x.
 * .@.@@@@.x.
 * .x@@@@@@.x
 * .x.@.@.@@@
 * ..@@@.@@@@
 * .x@@@@@@@.
 * ....@@@...
 *
 * Remove 7 rolls of paper:
 * ..........
 * .x@.....x.
 * .@@@@...xx
 * ..@@@@....
 * .x.@@@@...
 * ..@@@@@@..
 * ...@.@.@@x
 * ..@@@.@@@@
 * ..x@@@@@@.
 * ....@@@...
 *
 * Remove 5 rolls of paper:
 * ..........
 * ..x.......
 * .x@@@.....
 * ..@@@@....
 * ...@@@@...
 * ..x@@@@@..
 * ...@.@.@@.
 * ..x@@.@@@x
 * ...@@@@@@.
 * ....@@@...
 *
 * Remove 2 rolls of paper:
 * ..........
 * ..........
 * ..x@@.....
 * ..@@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@x.
 * ....@@@...
 *
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ...@@.....
 * ..x@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 *
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ...x@.....
 * ...@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 *
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ....x.....
 * ...@@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 *
 * Remove 1 roll of paper:
 * ..........
 * ..........
 * ..........
 * ...x@@....
 * ...@@@@...
 * ...@@@@@..
 * ...@.@.@@.
 * ...@@.@@@.
 * ...@@@@@..
 * ....@@@...
 * Stop once no more rolls of paper are accessible by a forklift. In this example, a total of 43 rolls of paper
 * can be removed.
 *
 * Start with your original diagram. How many rolls of paper in total can be removed by the Elves and their forklifts?
 */
class Problem04 {
    fun solvePart1(): Int {
        val map = mutableListOf<CharArray>()
        InputFileSequence("problem04.txt").use { ife ->
            ife.forEach { map.add(it.toCharArray()) }
        }
        return solvePart1(map)
    }

    fun solvePart2(): Int {
        val map = mutableListOf<CharArray>()
        InputFileSequence("problem04.txt").use { ife ->
            ife.forEach { map.add(it.toCharArray()) }
        }
        return solvePart2(map)
    }

    internal fun solvePart1(map: List<CharArray>): Int {
        var accessibleRolls = 0
        accessibleRolls += (0..<map[0].size)
            .filter { map[0][it] == ROLL }
            .count { isBorderRollAccessible(map, 0, it) }
        for (columnIdx in setOf(0, map[0].size - 1)) {
            accessibleRolls += (1..<map.size)
                .filter { map[it][columnIdx] == ROLL }
                .count { isBorderRollAccessible(map, it, columnIdx) }
        }
        accessibleRolls += (1..<map[0].size-1)
            .filter { map[map.size - 1][it] == ROLL }
            .count { isBorderRollAccessible(map, map.size - 1, it) }
        for (rowIdx in 1..<map.size - 1) {
            for (columnIdx in 1..<map[0].size - 1) {
                if (map[rowIdx][columnIdx] == ROLL && isNonBorderRollAccessible(map, rowIdx, columnIdx)) {
                    accessibleRolls++
                }
            }
        }
        return accessibleRolls
    }

    internal fun solvePart2(map: List<CharArray>): Int {
        var totalAccessibleRolls = 0

        val accessibleRollRowIdx = mutableListOf<Int>()
        val accessibleRollColumnIdx = mutableListOf<Int>()
        var accessibleRollsOnCurrIter: Int
        do {
            accessibleRollsOnCurrIter = 0
            (0..<map[0].size)
                .filter { map[0][it] == ROLL }
                .forEach {
                    if (isBorderRollAccessible(map, 0, it)) {
                        accessibleRollsOnCurrIter++
                        accessibleRollRowIdx.add(0)
                        accessibleRollColumnIdx.add(it)
                    }
                }
            for (columnIdx in setOf(0, map[0].size - 1)) {
                (1..<map.size)
                    .filter { map[it][columnIdx] == ROLL }
                    .forEach {
                        if (isBorderRollAccessible(map, it, columnIdx)) {
                            accessibleRollsOnCurrIter++
                            accessibleRollRowIdx.add(it)
                            accessibleRollColumnIdx.add(columnIdx)
                        }
                    }
            }
            (1..<map[0].size-1)
                .filter { map[map.size - 1][it] == ROLL }
                .forEach {
                    if (isBorderRollAccessible(map, map.size - 1, it)) {
                        accessibleRollsOnCurrIter++
                        accessibleRollRowIdx.add(map.size - 1)
                        accessibleRollColumnIdx.add(it)
                    }
                }
            for (rowIdx in 1..<map.size - 1) {
                for (columnIdx in 1..<map[0].size - 1) {
                    if (map[rowIdx][columnIdx] == ROLL && isNonBorderRollAccessible(map, rowIdx, columnIdx)) {
                        accessibleRollsOnCurrIter++
                        accessibleRollRowIdx.add(rowIdx)
                        accessibleRollColumnIdx.add(columnIdx)
                    }
                }
            }
            totalAccessibleRolls += accessibleRollsOnCurrIter
            discardRolls(map, accessibleRollRowIdx, accessibleRollColumnIdx)
        } while (accessibleRollsOnCurrIter > 0)

        return totalAccessibleRolls
    }

    private fun discardRolls(map: List<CharArray>, rowIdx: List<Int>, columnIdx: List<Int>) {
        for (i in rowIdx.indices) {
            map[rowIdx[i]][columnIdx[i]] = EMPTY
        }
    }

    private fun isNonBorderRollAccessible(map: List<CharArray>, rowIdx: Int, columnIdx: Int): Boolean {
        var surroundingRollsCount = 0
        for (c in columnIdx-1..columnIdx+1) {
            for (r in rowIdx-1..rowIdx+1 step 2) {
                if (map[r][c] == ROLL) {
                    surroundingRollsCount++
                    if (surroundingRollsCount == MAX_SURROUNDING_ROLL_COUNT) {
                        return false
                    }
                }
            }
        }
        if (map[rowIdx][columnIdx - 1] == ROLL) {
            surroundingRollsCount++
        }
        if (surroundingRollsCount == MAX_SURROUNDING_ROLL_COUNT) {
            return false
        }
        if (map[rowIdx][columnIdx + 1] == ROLL) {
            surroundingRollsCount++
        }
        return surroundingRollsCount < MAX_SURROUNDING_ROLL_COUNT
    }

    private fun isBorderRollAccessible(map: List<CharArray>, rowIdx: Int, columnIdx: Int): Boolean {
        val rowIdx2check = mutableListOf<Int>()
        val columnIdx2check = mutableListOf<Int>()
        if (rowIdx > 0) {
            rowIdx2check.add(rowIdx - 1)
            columnIdx2check.add(columnIdx)
            if (columnIdx > 0) {
                rowIdx2check.add(rowIdx - 1)
                columnIdx2check.add(columnIdx - 1)
            }
            if (columnIdx < map[0].size - 1) {
                rowIdx2check.add(rowIdx - 1)
                columnIdx2check.add(columnIdx + 1)
            }
        }
        if (rowIdx < map.size - 1) {
            rowIdx2check.add(rowIdx + 1)
            columnIdx2check.add(columnIdx)
            if (columnIdx > 0) {
                rowIdx2check.add(rowIdx + 1)
                columnIdx2check.add(columnIdx - 1)
            }
            if (columnIdx < map[0].size - 1) {
                rowIdx2check.add(rowIdx + 1)
                columnIdx2check.add(columnIdx + 1)
            }
        }
        if (columnIdx > 0) {
            rowIdx2check.add(rowIdx)
            columnIdx2check.add(columnIdx - 1)
        }
        if (columnIdx < map[0].size - 1) {
            rowIdx2check.add(rowIdx)
            columnIdx2check.add(columnIdx + 1)
        }
        var surroundingRollsCount = 0
        for (i in rowIdx2check.indices) {
            if (map[rowIdx2check[i]][columnIdx2check[i]] == ROLL) {
                surroundingRollsCount++
            }
        }
        return surroundingRollsCount < MAX_SURROUNDING_ROLL_COUNT
    }

    companion object {
        const val ROLL = '@'
        const val EMPTY = '.'
        const val MAX_SURROUNDING_ROLL_COUNT = 4
    }
}