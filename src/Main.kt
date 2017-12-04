fun main(args: Array<String>) {
    val result = CDiophantine(intArrayOf(5, 3),30,10,20).doit()?.multipliers
    if (result == null) System.out.println("Solution not found.")
    else {
        System.out.println(result.joinToString())
    }
}
