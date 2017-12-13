import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows

/**
 * This is CDiophantineTest.
 */
internal class CDiophantineTest {
    @Test
    fun doIt() {
        //sample of successful executions (size - 100)
        var successPercents: IntArray = intArrayOf()

        for (j in 1..100) {
            // success of execution of 100 invocations in percent
            var success = 0
            for (i in 1..100) {
                val result = CDiophantine(intArrayOf(1,2,3,4),30,20,20).doIt()
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

    //I don't know why, but this function returns suspiciously good results
    //(JVM effects or one-time creation of GA object may be)
    //also it's running much more slowly (about 3 minutes against 5-15 seconds)
    fun <R : GA.GASolution> gaTestWrapper(func: GA<R>) {
        //sample of successful executions (size - 100)
        var successPercents: IntArray = intArrayOf()

        for (j in 1..100) {
            // success of execution of 100 invocations in percent
            var success = 0
            for (i in 1..100) {
                if (func.doIt() != null) success++
            }
            successPercents += success
        }

        //counting of average percent of successful executions
        val averagePercent = successPercents.average()

        //counting of error of average percent
        var sumSquare = 0.0
        for (p in successPercents) {
            sumSquare += Math.pow(averagePercent - p, 2.0)
        }
        val error = 2 * Math.sqrt(sumSquare/9900) // confidential probability 95%
        System.out.println("Average percent of successful results: $averagePercent")
        System.out.format("Error: %.2f", error)
    }

}