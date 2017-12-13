import java.util.*

class CDiophantine(val factors: IntArray,
                   val result: Int,
                   override val populationSize: Int,
                   override val generations: Int) : GA<CDiophantine.Solution>() {
    override var resultGA: Solution? = null
    private val rand = Random()

    inner class Solution : GASolution {
        var multipliers: IntArray

        override var fitness: Int = -1

        constructor(multipliers: IntArray) {
            this.multipliers = multipliers
            fitness()
        }

        override fun fitness() {
            fitness = Math.abs(multipliers.foldIndexed(0)
            { index, result, m -> result + factors[index] * m} - result)
        }

        override fun mutation(): GASolution {
            val index = rand.nextInt(multipliers.size)
            val newMultipliers = multipliers.copyOf()
            newMultipliers[index] = 1 + rand.nextInt(result - 1)
            return Solution(newMultipliers)
        }

        override fun crossover(other: GASolution): GASolution {
            val index = rand.nextInt(factors.size - 1)
            val newOther = other as Solution
            val newMultipliers: IntArray
            newMultipliers = if (rand.nextBoolean()) this.multipliers.copyOfRange(0, index) +
                    newOther.multipliers.copyOfRange(index, factors.size)
            else this.multipliers.copyOfRange(0, index) +
                    newOther.multipliers.copyOfRange(index, factors.size)
            return Solution(newMultipliers)
        }

    }

    override fun initialPopulation() {
        check(factors.isNotEmpty()) { "No factors." }
        check(populationSize > 0) {"Population size can't be negative or zero."}
        check(generations > 0) {"Generations count can't be negative or zero."}
        if (factors.size == 1) {
            check(result % factors[0] != 0) {"No integer solution."}
            resultGA = Solution(intArrayOf(result / factors[0]))
            return
        }

        for (i in 0..populationSize - 1) {
            var temp: IntArray = intArrayOf()
            for (k in 0..factors.size - 1) {
                temp += 1 + rand.nextInt(result)
            }
            population.add(Solution(temp))
        }
    }
}