private val equations = arrayOf(
    arrayOf(1.0, 2.0, 3.0, 4.0),
    arrayOf(2.0, 1.0, 3.0, 4.0),
    arrayOf(3.0, 2.0, 1.0, 4.0),
    arrayOf(4.0, 3.0, 2.0, 1.0)
)

//private val equations = arrayOf(
//    arrayOf(2.0, -1.0, -2.0),
//    arrayOf(-4.0, 6.0, 3.0),
//    arrayOf(-4.0, -2.0, 8.0),
//)

private val equality = arrayOf(10.0, 10.0, 10.0, 10.0)

fun main() {
    val (lower, upper) = decompose(equations)

    println("Lower")
    lower.forEach { println(it.joinToString()) }

    println("Upper")
    upper.forEach { println(it.joinToString()) }

    println("Validation")
    (lower * upper).forEach { println(it.joinToString()) }

    val yVector = calculateYVector(lower, equality)
    println("Y")
    println(yVector.joinToString())

    val roots = calculateRootVector(upper, yVector)
    println("X")
    println(roots.joinToString())
}

private fun decompose(source: Array<Array<Double>>): Pair<Array<Array<Double>>, Array<Array<Double>>> {
    val lower = Array(source.size) { Array(source.size) { .0 } }
    val upper = Array(source.size) { Array(source.size) { .0 } }

    for (i in source.indices) {
        lower[i][i] = 1.0

        for (j in i..<source[i].size) {
            upper[i][j] = source[i][j]

            for (k in 0..<i) {
                upper[i][j] -= lower[i][k] * upper[k][j]
            }

            if (j != i) {
                lower[j][i] = source[j][i]

                for (k in 0..<i) {
                    lower[j][i] -= lower[j][k] * upper[k][i]
                }

                lower[j][i] /= upper[i][i]
            }
        }
    }

    return Pair(lower, upper)
}

private fun calculateYVector(triangle: Array<Array<Double>>, result: Array<Double>): Array<Double> {
    val yValues = result.copyOf()

    for (i in triangle.indices) {
        for (j in 0..<i) {
            yValues[i] -= yValues[j] * triangle[i][j]
        }
    }

    return yValues
}

private fun calculateRootVector(triangle: Array<Array<Double>>, yVector: Array<Double>): Array<Double> {
    val roots = yVector.copyOf()

    for (i in roots.size - 1 downTo 0) {
        for (j in i + 1..<roots.size) {
            roots[i] -= roots[j] * triangle[i][j]
        }

        roots[i] /= triangle[i][i]
    }

    return roots
}

private operator fun Array<Array<Double>>.times(b: Array<Array<Double>>): Array<Array<Double>> {
    val result = Array(this.size) { Array(b.size) { .0 } }

    for (i in this.indices) {
        for (j in this[i].indices) {
            for (k in this[i].indices) {
                result[i][j] += this[i][k] * b[k][j]
            }
        }
    }

    return result
}