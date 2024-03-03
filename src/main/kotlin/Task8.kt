import kotlin.math.*

private const val Eps = .0001

private val xRange = Pair(.0, 1.0)
private val yRange = Pair(.0, 3.0)

private fun functionX(y: Double) = (1.0 + sin(y - .5)) / 2.0
private fun functionY(x: Double) = 1.5 - cos(x)

fun main() {
    var x = xRange.first
    var y = yRange.first

    do {
        val nextX = functionX(y)
        val nextY = functionY(x)

        val diffX = abs(nextX - x)
        val diffY = abs(nextY - y)

        println("Diff x: $diffX")
        println("Diff y: $diffY")

        x = nextX
        y = nextY
    } while (diffX > Eps || diffY > Eps)

    println("X: $x")
    println("Y: $y")
}
