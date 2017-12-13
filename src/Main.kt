fun main(args: Array<String>) {
//    val result = CDiophantine(intArrayOf(1,2,3,4), 30, 20, 20).doIt()?.multipliers
    val result = Knapsack(arrayListOf(1 to 5,
            3 to 7,
            6 to 11,
            13 to 13,
            14 to 17),
            20, 20, 20).doIt()?.knapsack


    if (result == null) System.out.println("Solution not found.")
    else {
//        System.out.println(result.joinToString())
        System.out.println(result)
    }
}
