
abstract class GA<T> {

    protected abstract val generations: Int
    protected abstract var resultGA: T?
    protected abstract val populationSize: Int

    protected abstract fun initialPopulation()
    protected abstract fun fitness(specimen: T): Int
    protected abstract fun mutation(specimen: T)
    protected abstract fun crossover(specimen1: T, specimen2: T): T
    protected abstract fun reproduction()
    protected abstract fun selection()

    fun doit(): T? {
        this.initialPopulation()
        for (i in 0..generations) {
            this.reproduction()
            if (resultGA != null) return resultGA
            this.selection()
        }
        return resultGA
    }
}