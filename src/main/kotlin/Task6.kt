import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign

private const val Eps = .001
private const val step = 1.0
private val baseRange = Pair(-100.0, 100.0)

private fun function(x: Double): Double = x.pow(3) - 3 * x * x + 6 * x + 3

private fun firstDerivative(x: Double) = 3 * x * x - 6 * x + 6

private fun secondDerivative(x: Double) = 6 * x - 6

fun main() {
    val ranges = findRootRanges(baseRange.first, baseRange.second, step, ::function)

    for ((start, end) in ranges) {
        println("Range: [$start, $end]")

        println("Chord")
        println("Result: ${findByChord(start, end, Eps, ::function, ::secondDerivative)}")

        println("Newton")
        println("Result: ${findByNewton(start, end, Eps, ::function, ::firstDerivative, ::secondDerivative)}")
    }
}

private fun findRootRanges(
    start: Double,
    end: Double,
    step: Double,
    function: (x: Double) -> Double
): List<Pair<Double, Double>> {
    val ranges = mutableListOf<Pair<Double, Double>>()

    var value = function(start)
    var x = start + step

    while (x <= end) {
        val newValue = function(x)
        if (sign(newValue) != sign(value)) ranges.add(Pair(x - step, x))
        value = newValue
        x += step
    }

    return ranges
}

private fun findByChord(
    start: Double,
    end: Double,
    eps: Double,
    function: (x: Double) -> Double,
    secondDerivative: (x: Double) -> Double
): Double {
    var x: Double
    var c: Double

    if (function(start) * secondDerivative(start) > 0) {
        x = end
        c = start
    } else {
        x = start
        c = end
    }

    do {
        val nextX = x - function(x) * (c - x) / (function(c) - function(x))
        val diff = abs(nextX - x)
        x = nextX
        println("Eps: $diff")
    } while (diff > eps)

    return x
}

private fun findByNewton(
    start: Double,
    end: Double,
    eps: Double,
    function: (x: Double) -> Double,
    firstDerivative: (x: Double) -> Double,
    secondDerivative: (x: Double) -> Double
): Double {
    var x = if (function(start) * secondDerivative(start) > 0) start else end

    do {
        val nextX = x - function(x) / firstDerivative(x)
        val diff = abs(nextX - x)
        x = nextX
        println("Eps: $diff")
    } while (diff > eps)

    return x
}