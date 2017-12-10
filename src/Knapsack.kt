import java.util.*

class Knapsack(val items: List<Pair<Int, Int>>,
               val maxLoad: Int,
               override val populationSize: Int,
               override val generations: Int) : GA<Knapsack.Specimen>() {
    override var resultGA: Specimen? = null
    private val rand = Random()

    data class Specimen(var knapsack: BitSet, var fitness: Int = -1)

    override fun initialPopulation() {
        check(items.isNotEmpty() && populationSize != 0 && generations != 0)
        for (j in 0..populationSize - 1) {
            val temp = BitSet(items.size)
            for (i in 0..items.size - 1) temp[i] = rand.nextBoolean()
            val specimen = Specimen(temp)
            specimen.fitness = fitness(specimen)
            population += specimen
        }
    }

    override fun fitness(specimen: Specimen): Int {
        var sumWeight = 0
        var sumCost = 0
        for (i in 0..items.size - 1) {
            if (specimen.knapsack[i]) {
                sumWeight += items[i].first
                sumCost += items[i].second
            }
        }
        val freeSpace = maxLoad - sumWeight
        return if(freeSpace >= 0) - sumCost
        else sumCost
    }

    override fun mutation(specimen: Specimen) {
        val index = rand.nextInt(items.size)
        specimen.knapsack[index] = specimen.knapsack[index].not()
        specimen.fitness = fitness(specimen)
    }

    override fun crossover(specimen1: Specimen, specimen2: Specimen): Specimen {
        val index = 1 + rand.nextInt(items.size - 2)
        val result: Specimen
        val temp = BitSet(items.size)
        if (rand.nextBoolean()) {
            for (i in 0..items.size -1) {
                if (i < index && specimen1.knapsack[i]) temp.set(i)
                else if (i >= index && specimen2.knapsack[i]) temp.set(i)
            }
        }
        else {
            for (i in 0..items.size -1) {
                if (i < index && specimen2.knapsack[i]) temp.set(i)
                else if (i >= index && specimen1.knapsack[i]) temp.set(i)
            }
        }
        result = Specimen(temp)
        result.fitness = fitness(result)
        return result
    }

    override fun doIt(): Specimen? {
        this.initialPopulation()
        for (i in 0..generations - 1) {
            this.reproduction()
            this.selection()
        }
        return if (population[0].fitness <= 0) population[0]
        else null
    }
}