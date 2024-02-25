import kotlin.math.abs

//private val equations = arrayOf(
//    arrayOf(10.0, 2.0, 3.0, 1.0),
//    arrayOf(2.0, 10.0, 3.0, 1.0),
//    arrayOf(3.0, 2.0, 10.0, 1.0),
//    arrayOf(1.0, 3.0, 2.0, 10.0)
//)
//
//private val equality = arrayOf(10.0, 10.0, 10.0, 10.0)

private val equations = arrayOf(
    arrayOf(.21, .12, -.34, -.16),
    arrayOf(.34, -.08, .17, -.18),
    arrayOf(.16, .34, .15, -.31),
    arrayOf(.12, -.26, -.08, .25)
)

private val equality = arrayOf(-.64, 1.42, -.42, .83)

private const val Eps = .0000000001

fun main() {
    var eps: Double
    var roots = Array(equations.size) { .0 }
    //option 1
//    val (multipliers, freeElements) = calculateMultipliers(equations, equality)

    //option 2
    val multipliers = -equations
    val freeElements = -equality

    do {
        val nextRoots = calculateRoots(multipliers, freeElements, roots)
        eps = calculateEps(nextRoots, roots)
        roots = nextRoots
        println("Eps: $eps")
    } while (eps > Eps)

    println("Roots: ${roots.joinToString()}")
    println("EPS: ${validateResult(multipliers, freeElements, roots)}")
}

private fun calculateMultipliers(
    sourceMultipliers: Array<Array<Double>>,
    sourceFree: Array<Double>
): Pair<Array<Array<Double>>, Array<Double>> {
    val multipliers = Array(sourceMultipliers.size) { Array(sourceMultipliers.size) { .0 } }
    val freeElements = Array(sourceMultipliers.size) { .0 }

    for (i in multipliers.indices) {
        for (j in multipliers[i].indices) {
            multipliers[i][j] = if (i == j) .0 else sourceMultipliers[i][j] / sourceMultipliers[i][i]
        }

        freeElements[i] = sourceFree[i] / sourceMultipliers[i][i]
    }

    return Pair(multipliers, freeElements)
}

private fun calculateEps(vector1: Array<Double>, vector2: Array<Double>): Double {
    var eps = .0

    for (i in vector1.indices) {
        val diff = abs(vector1[i] - vector2[i])

        if (diff > eps) eps = diff
    }

    return eps
}

private fun calculateRoots(
    multipliers: Array<Array<Double>>,
    freeElements: Array<Double>,
    previousRoots: Array<Double>
): Array<Double> {
    val nextRoots = freeElements.copyOf()
    for (i in multipliers.indices) {
        for (j in multipliers[i].indices) {
            nextRoots[i] -= previousRoots[j] * multipliers[i][j]
        }
    }

    return nextRoots
}

private fun validateResult(
    multipliers: Array<Array<Double>>,
    freeElements: Array<Double>,
    roots: Array<Double>
): Double {
    var eps = .0

    for (i in multipliers.indices) {
        var x = freeElements[i]
        for (j in multipliers[i].indices) {
            x += multipliers[i][j] * roots[j]
        }

        val diff = abs(roots[i] - x)
        if (eps < diff) eps = diff
    }

    return eps
}

private operator fun Array<Array<Double>>.unaryMinus() = this.map { it.map { -it }.toTypedArray() }.toTypedArray()

private operator fun Array<Double>.unaryMinus() = this.map { -it }.toTypedArray()