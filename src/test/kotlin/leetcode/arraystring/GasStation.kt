package leetcode.arraystring

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


// https://leetcode.com/problems/gas-station/description/?envType=study-plan-v2&envId=top-interview-150

/*
There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique.



Example 1:

Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.

Example 2:

Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.

Constraints:
    n == gas.length == cost.length
    1 <= n <= 10^5
    0 <= gas[i], cost[i] <= 10^4

Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Input: gas = [2,3,4], cost = [3,4,3]
Output: -1

man len is 10^5

gas += gas[i]
gas -= cost[i]

starting location is valid if gas[i] >= cost[i]
There's an o(n^2) brute force algorithm for this
for start in gas.indices:
    totalGas = 0
    if canCompleteCycleStartingAt(start, gas, cost)
fun canCompleteCycle -> Boolean
    for i in start to just before start (circular array iteration):
        totalGas += gas[i]
        if (totalGas >= cost[i])
            totalGas -= cost[i]
         else false
    return true

 for j in indices:
    val i = j + start % size

Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
-2 -2 -2 3 3 = 0

Input: gas = [2,3,4], cost = [3,4,3]
-1 -1 1 = -1

how to identify the starting location w/o brute force
1. Is there some mathematical property about the sequence that we can exploit?
I don't know it, but maybe can identify it.
We know an array that sums to >= 0 has a solution.
How do we find the solution once we know it exists?
It will be an index where g[i] >= c[i]
but that leaves many candidates

2. Is there another way to scan? This question is isomorphic to 1.

gas and cost are same length
0 <= 10^4

I wonder if I can eliminate sections of the array?
We know a solution is guaranteed to be unique (maybe by selection of input)
The uniqueness is possibly exploitable
Consider 0,0,0,0 0,0,0,0 does not have a unique solution; all indices are solutions

Remove all negative

Track indices of positive values

-2 -2 -2 3 3 = 0

left sum = -6
right sum = 3
pos: 3
if sums to 0 -> correct

if positive or 0

 */

private class GasStationSolution {
    fun canCompleteCircuit(gas: IntArray, cost: IntArray): Int {
        if (gas.sum() < cost.sum()) return -1
        var start = 0
        var currentGas = 0
        gas.indices.forEach { i ->
            currentGas += (gas[i] - cost[i])
            if (currentGas < 0) {
                start = i + 1
                currentGas = 0
            }
        }
        return start
    }
}

private data class GasStationTestCase(
    val gas: IntArray,
    val cost: IntArray,
    val expected: Int
)

private class GasStationTest {
    companion object {
        @JvmStatic
        fun getData() = listOf(
            GasStationTestCase(
                intArrayOf(1,2,3,4,5),
                intArrayOf(3,4,5,1,2),
                3
            ),
            GasStationTestCase(
                intArrayOf(2,3,4),
                intArrayOf(3,4,3),
                -1
            ),
            GasStationTestCase(
                intArrayOf(5,1,2,3,4),
                intArrayOf(4,4,1,5,1),
                4
            )
        )
    }

    @ParameterizedTest
    @MethodSource("getData")
    fun test(testCase: GasStationTestCase) {
        val actual = GasStationSolution().canCompleteCircuit(testCase.gas, testCase.cost)
        Assertions.assertEquals(testCase.expected, actual)
    }
}