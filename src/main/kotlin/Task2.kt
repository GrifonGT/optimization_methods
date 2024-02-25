private val equations = arrayOf(
    arrayOf(1.0, 2.0, 3.0, 4.0),
    arrayOf(2.0, 1.0, 3.0, 4.0),
    arrayOf(3.0, 2.0, 1.0, 4.0),
    arrayOf(4.0, 3.0, 2.0, 1.0)
)

private val equality = arrayOf(10.0, 10.0, 10.0, 10.0)

fun main() {
    validateEquationsInput(equations, equality)

    val results = equality.copyOf()
    val triangleMatrix = makeTriangle(equations, results)

    val roots = calculateRootVector(triangleMatrix, results)
    println(roots.joinToString())
}

private fun makeTriangle(sourceMatrix: Array<Array<Double>>, sourceResult: Array<Double>): Array<Array<Double>> {
    val matrix = sourceMatrix.copyOf()

    for (i in 0..<matrix.size - 1) {
        for (j in i + 1..<matrix.size) {
            val multiplier = matrix[j][i] / matrix[i][i]

            for (k in i..<matrix.size) {
                matrix[j][k] -= matrix[i][k]* multiplier
            }

            sourceResult[j] -= sourceResult[i] * multiplier
        }
    }

    return matrix
}

private fun calculateRootVector(triangle: Array<Array<Double>>, result: Array<Double>): Array<Double> {
    val xValues = result.copyOf()

    for (i in xValues.size - 1 downTo 0) {
        for (j in xValues.size - 1 downTo i + 1) {
            xValues[i] -= xValues[j] * triangle[i][j]
        }

        xValues[i] /= triangle[i][i]
    }

    return xValues
}