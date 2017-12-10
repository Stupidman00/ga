import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

/**
 * This is KnapsackTest.
 */
internal class KnapsackTest {
    @Test
    fun doIt() {
        //sample of successful executions (size - 100)
        var successPercents: IntArray = intArrayOf()

        for (j in 1..100) {
            // success of execution of 100 invocations in percent
            var success = 0
            for (i in 1..100) {
                val result = Knapsack(arrayListOf(1 to 5,
                        3 to 7,
                        6 to 11,
                        13 to 13,
                        14 to 17),
                        20, 20, 20).doIt()
                if (result != null) success++
            }
            successPercents += success
        }

        //counting of average percent of successful executions
        val averagePercent = successPercents.average()

        //counting of error of average percent
        val sumSquare = successPercents.sumByDouble { Math.pow(averagePercent - it, 2.0) }
        val error = 2 * Math.sqrt(sumSquare/9900) // confidential probability 95%
        System.out.println("Average percent of successful results: $averagePercent")
        System.out.format("Error: %.2f", error)
    }

}