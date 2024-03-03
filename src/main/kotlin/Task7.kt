import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign

private const val Eps = .001
private const val step = 1.0
private val baseRange = Pair(-100.0, 100.0)

private fun function(x: Double): Double = x.pow(3) - 3 * x * x + 6 * x + 3

private fun firstDerivative(x: Double) = 3 * x * x - 6 * x + 6

private fun secondDerivative(x: Double) = 6 * x - 6

private fun functionWithExpressedX(x: Double): Double = - x.pow(3) / 6 + x * x / 2 - .5

fun main() {
    val ranges = findRootRanges(baseRange.first, baseRange.second, step, ::function)

    for ((start, end) in ranges) {
        println("Range: [$start, $end]")

        println("Mixed")
        println("Result: ${findByMixed(start, end, Eps, ::function, ::secondDerivative)}")

        println("Simple iteration")
        println("Result: ${simpleIterationFirstWay(start, Eps, ::functionWithExpressedX)}")
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

private fun findByMixed(
    start: Double,
    end: Double,
    eps: Double,
    function: (x: Double) -> Double,
    secondDerivative: (x: Double) -> Double
): Double {
    var u: Double
    var v: Double

    if (function(start) * secondDerivative(start) > 0) {
        u = end
        v = start
    } else {
        u = start
        v = end
    }

    do {
        val nextU = u - function(u) * (v - u) / (function(v) - function(u))
        val nextV = v - function(v) / firstDerivative(v)
        val diff = abs(v - u)
        u = nextU
        v = nextV
        println("Diff: $diff")
    } while (diff > eps)

    return (u + v) / 2
}

//private fun findBySimpleIteration(
//    start: Double,
//    end: Double,
//    eps: Double,
//    function: (x: Double) -> Double,
//    firstDerivative: (x: Double) -> Double,
//    secondDerivative: (x: Double) -> Double,
//    functionFi: (x: Double) -> Double
//): Double {
//    val maxDerivative = abs(findMaxDerivativeValue(start, end, 1000, firstDerivative))
//
//    if (maxDerivative <= 1.0)
//}

private fun simpleIterationFirstWay(
    startValue: Double,
    eps: Double,
    functionFi: (x: Double) -> Double
): Double {
    var x = startValue

    do {
        val nextX = functionFi(x)
        val diff = abs(nextX - x)
        x = nextX
        println("diff: $diff")
    } while (diff > eps)

    return x
}
//
//private fun findMaxDerivativeValue(start: Double, end: Double, split: Int, derivative: (x: Double) -> Double): Double {
//    var result = derivative(start)
//    val step = (end - start) / split
//
//    for (x in 1..split) {
//        val value = derivative(start + x * step)
//        if (value > result) result = value
//    }
//
//    return result
//}