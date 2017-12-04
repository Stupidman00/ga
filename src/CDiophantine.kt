import java.util.*
import kotlin.Comparator

class CDiophantine(val coefs: IntArray,
                   val result: Int,
                   override val populationSize: Int,
                   override val generations: Int) : GA<CDiophantine.Specimen>() {
    override var resultGA: Specimen? = null

    data class Specimen(var multipliers: IntArray, var fitness: Int = -1) {
        constructor() : this(intArrayOf())
    }

    var population: Array<Specimen> = emptyArray()

    override fun initialPopulation() {
        val rand = Random()
        for (i in 0..populationSize - 1) {
            var temp: IntArray = intArrayOf()
            for (k in 0..coefs.size - 1) {
                temp += 1 + rand.nextInt(result)
            }
            val specimen = Specimen(temp)
            specimen.fitness = fitness(specimen)
            population += specimen
        }
    }

    override fun fitness(specimen: Specimen): Int {
        var sum = 0
        for (i in 0..coefs.size - 1) {
            sum += coefs[i] * specimen.multipliers[i]
        }
        return Math.abs(sum - result)
    }

    override fun mutation(specimen: Specimen) {
        val rand = Random()
        val index = rand.nextInt(coefs.size)
        specimen.multipliers[index] = 1 + rand.nextInt(result)
        specimen.fitness = fitness(specimen)
    }

    override fun crossover(specimen1: Specimen, specimen2: Specimen): Specimen {
        val rand = Random()
        val index = rand.nextInt(coefs.size)
        val newMultipliers1 = specimen1.multipliers.copyOfRange(0, index) +
                specimen2.multipliers.copyOfRange(index, coefs.size)
        val newMultipliers2 = specimen2.multipliers.copyOfRange(0, index) +
                specimen1.multipliers.copyOfRange(index, coefs.size)
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

    override fun reproduction() {
        population.forEach { sp ->
            if (sp.fitness == 0) {
                resultGA = sp
                return
            }
        }

        val rand = Random()
        val mutationNumber = rand.nextInt((20 * populationSize * coefs.size) / 100)
        for (i in 0..mutationNumber - 1) {
            val index = rand.nextInt(population.size)
            mutation(population[index])
        }

        val size = population.size
        for (p1 in 0..size - 1) {
            var p2 = rand.nextInt(population.size)
            while (p2 == p1) p2 = rand.nextInt(population.size)
            population += crossover(population[p1], population[p2])
        }
    }

    override fun selection() {
        population.sortWith(object : Comparator<Specimen> {
            override fun compare(o1: Specimen?, o2: Specimen?): Int {
                if (o1 == null || o2 == null) throw NullPointerException()
                return compareValues(o1.fitness, o2.fitness)
            }
        })
        population = population.copyOfRange(0, populationSize)
    }
}