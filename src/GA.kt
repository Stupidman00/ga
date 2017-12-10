import java.util.*

abstract class GA<T> {

    protected abstract val generations: Int
    protected abstract var resultGA: T?
    protected abstract val populationSize: Int
    var population: List<T> = emptyList()


    protected abstract fun initialPopulation()
    protected abstract fun fitness(specimen: T): Int
    protected abstract fun mutation(specimen: T)
    protected abstract fun crossover(specimen1: T, specimen2: T): T

    protected open fun reproduction() {
        population.forEach { sp ->
            if (fitness(sp) == 0) {
                resultGA = sp
                return
            }
        }

        val rand = Random()
        val mutationNumber = rand.nextInt(populationSize)
        for (i in 0..mutationNumber) {
            val index = rand.nextInt(populationSize)
            mutation(population[index])
        }

        for (p1 in 0..populationSize - 1) {
            var p2 = rand.nextInt(populationSize)
            while (p2 == p1) p2 = rand.nextInt(populationSize)
            population += crossover(population[p1], population[p2])
        }
    }

    protected open fun selection() {
        population = population.sortedBy {sp -> fitness(sp)}.subList(0, populationSize)
    }

    open fun doIt(): T? {
        this.initialPopulation()
        if (resultGA != null) return resultGA
        for (i in 0..generations - 1) {
            this.reproduction()
            if (resultGA != null) return resultGA
            this.selection()
        }
        return resultGA
    }
}