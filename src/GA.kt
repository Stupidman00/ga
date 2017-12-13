import java.util.*

abstract class GA<T : GA.GASolution> {

    protected abstract val generations: Int
    protected abstract var resultGA: T?
    protected abstract val populationSize: Int
    var population: MutableList<T> = mutableListOf()

    interface GASolution {
        var fitness: Int

        fun fitness()
        fun mutation(): GASolution
        fun crossover(other: GASolution) : GASolution
    }

    protected abstract fun initialPopulation()

    protected open fun reproduction() {
        population.forEach { sp ->
            if (sp.fitness == 0) {
                resultGA = sp
                return
            }
        }

        val rand = Random()
        val mutationNumber = rand.nextInt(populationSize)
        for (i in 0..mutationNumber) {
            val index = rand.nextInt(populationSize)
            population[index] = population[index].mutation() as T
        }

        for (p1 in 0..populationSize - 1) {
            var p2 = rand.nextInt(populationSize)
            while (p2 == p1) p2 = rand.nextInt(populationSize)
            population = population.plus<T>(population[p1].crossover(population[p2]) as T) as MutableList<T>
        }
    }

    protected open fun selection() {
        population = population.sortedBy {sp -> sp.fitness}.subList(0, populationSize) as MutableList<T>
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