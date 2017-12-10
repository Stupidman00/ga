fun main(args: Array<String>) {
    val result = Knapsack(arrayListOf(1 to 5,
            3 to 7,
            6 to 11,
            9 to 13,
            10 to 20,
            11 to 20,
            13 to 13,
            14 to 17,
            12 to 10,
            15 to 20),
            20, 20, 20).doIt()?.knapsack

    if (result == null) System.out.println("Solution not found.")
    else {
        System.out.println(result)
    }
}
