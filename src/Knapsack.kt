import java.util.*

class Knapsack(val items: List<Pair<Int, Int>>,
               val maxLoad: Int,
               override val populationSize: Int,
               override val generations: Int) : GA<Knapsack.Solution>() {
    override var resultGA: Solution? = null
    private val rand = Random()

    inner class Solution : GASolution {
        var knapsack: BitSet
        override var fitness: Int = -1

        constructor(knapsack: BitSet) {
            this.knapsack = knapsack
            fitness()
        }

        override fun fitness() {
            val sumWeight = items.foldIndexed(0)
            { index, result, item -> if (this.knapsack[index]) result + item.first else result }
            val sumCost = items.foldIndexed(0)
            { index, result, item -> if (this.knapsack[index]) result + item.second else result }
            val freeSpace = maxLoad - sumWeight
            this.fitness = if(freeSpace >= 0) - sumCost
            else sumCost
        }

        override fun mutation(): GASolution {
            val index = rand.nextInt(items.size)
            val newKnapsack = BitSet(items.size)
            newKnapsack.or(this.knapsack)
            newKnapsack.flip(index)
            return Solution(newKnapsack)
        }

        override fun crossover(other: GASolution): GASolution {
            val index = 1 + rand.nextInt(items.size - 2)
            val newOther = other as Solution
            val temp = BitSet(items.size)
            if (rand.nextBoolean()) {
                for (i in 0..items.size -1) {
                    if (i < index && this.knapsack[i]) temp.set(i)
                    else if (i >= index && newOther.knapsack[i]) temp.set(i)
                }
            }
            else {
                for (i in 0..items.size -1) {
                    if (i < index && newOther.knapsack[i]) temp.set(i)
                    else if (i >= index && this.knapsack[i]) temp.set(i)
                }
            }
            return Solution(temp)
        }
    }

    override fun initialPopulation() {
        check(items.isNotEmpty()) {"Empty items list."}
        check(populationSize > 0) {"Population size can't be negative or zero."}
        check(generations > 0) {"Generations count can't be negative or zero."}
        for (j in 0..populationSize - 1) {
            val temp = BitSet(items.size)
            for (i in 0..items.size - 1) {
                temp[i] = rand.nextBoolean()
            }
            population.add(Solution(temp))
        }
    }

    override fun doIt(): Solution? {
        this.initialPopulation()
        for (i in 0..generations - 1) {
            this.reproduction()
            this.selection()
        }
        return if (population[0].fitness <= 0) population[0]
        else null
    }
}