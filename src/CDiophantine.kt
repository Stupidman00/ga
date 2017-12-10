import java.util.*

class CDiophantine(val factors: IntArray,
                   val result: Int,
                   override val populationSize: Int,
                   override val generations: Int) : GA<CDiophantine.Specimen>() {
    override var resultGA: Specimen? = null

    data class Specimen (var multipliers: IntArray, var fitness: Int = -1)

    override fun initialPopulation() {
        if (factors.isEmpty()) throw IllegalArgumentException("No factors.")
        if (factors.size == 1) {
            if (result % factors[0] != 0) throw IllegalArgumentException("No integer solution.")
            else {
                resultGA = Specimen(intArrayOf(result / factors[0]),0)
                return
            }
        }
        val rand = Random()
        for (i in 0..populationSize - 1) {
            var temp: IntArray = intArrayOf()
            for (k in 0..factors.size - 1) {
                temp += 1 + rand.nextInt(result)
            }
            val specimen = Specimen(temp)
            specimen.fitness = fitness(specimen)
            population += specimen
        }
    }

    override fun fitness(specimen: Specimen): Int {
        var sum = 0
        for (i in 0..factors.size - 1) {
            sum += factors[i] * specimen.multipliers[i]
        }
        return Math.abs(sum - result)
    }

    override fun mutation(specimen: Specimen) {
        val rand = Random()
        val index = rand.nextInt(factors.size)
        specimen.multipliers[index] = 1 + rand.nextInt(result)
        specimen.fitness = fitness(specimen)
    }

    override fun crossover(specimen1: Specimen, specimen2: Specimen): Specimen {
        val rand = Random()
        val index = rand.nextInt(factors.size)
        val newMultipliers1 = specimen1.multipliers.copyOfRange(0, index) +
                specimen2.multipliers.copyOfRange(index, factors.size)
        val newMultipliers2 = specimen2.multipliers.copyOfRange(0, index) +
                specimen1.multipliers.copyOfRange(index, factors.size)
        return when (rand.nextInt() % 2) {
            0 -> {
                val specimen = Specimen(newMultipliers1)
                specimen.fitness = fitness(specimen)
                specimen
            }
            else -> {
                val specimen = Specimen(newMultipliers2)
                specimen.fitness = fitness(specimen)
                specimen
            }
        }
    }
}