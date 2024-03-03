import kotlin.math.sqrt

private const val Iterations = 10_000

private val range = Pair(1.2, 2.7)
private fun function(x: Double) = 1 / sqrt(x * x + 3.2)
fun main() {
    println("Left triangle: ${leftTriangle(range.first, range.second, Iterations, ::function)}")
    println("Right triangle: ${rightTriangle(range.first, range.second, Iterations, ::function)}")
    println("Trapeze triangle: ${trapeze(range.first, range.second, Iterations, ::function)}")
}

private fun leftTriangle(start: Double, end: Double, iterations: Int, function: (x: Double) -> Double): Double {
    val step = (end - start) / iterations
    var result = 0.0

    for (i in 0..<iterations) {
        result += function(start + step * i)
    }

    return result * step
}

private fun rightTriangle(start: Double, end: Double, iterations: Int, function: (x: Double) -> Double): Double {
    val step = (end - start) / iterations
    var result = 0.0

    for (i in 1..iterations) {
        result += function(start + step * i)
    }

    return result * step
}

private fun trapeze(start: Double, end: Double, iterations: Int, function: (x: Double) -> Double): Double {
    val step = (end - start) / iterations
    var result = (start + end) / 2

    for (i in 1..<iterations) {
        result += function(start + step * i)
    }

    return result * step
}