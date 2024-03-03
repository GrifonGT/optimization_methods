import kotlin.math.sqrt

private const val Iterations = 10_000

private val range = Pair(1.2, 2.7)
private fun function(x: Double) = 1 / sqrt(x * x + 3.2)

fun main() {
    println("Simpson: ${methodSimpson(range.first, range.second, Iterations, ::function)}")
}

private fun methodSimpson(start: Double, end: Double, iterations: Int, function: (x: Double) -> Double): Double {
    val step = (end - start) / iterations
    var result = (function(start) + function(end)) / 2

    for (i in 1..<iterations) {
        result += function(start + step * i) + 2 * function((start + step * (i - .5)))
    }

    result += 2 * function(start + step * (iterations - .5))
    return result * step / 3.0
}