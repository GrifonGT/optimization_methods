import java.lang.IllegalArgumentException

private val equations = arrayOf(
    arrayOf(1.0, 2.0, 3.0, 4.0),
    arrayOf(2.0, 1.0, 3.0, 4.0),
    arrayOf(3.0, 2.0, 1.0, 4.0),
    arrayOf(4.0, 3.0, 2.0, 1.0)
)

private val equality = arrayOf(10.0, 10.0, 10.0, 10.0)

fun main() {
    validateEquationsInput(equations, equality)

    val mainDeterminant = calculateDeterminant(equations)

    if (mainDeterminant == .0) throw IllegalStateException("Root not found. Determinant == 0")

    val results = calculateResult(equations, equality, mainDeterminant)
    println(results.joinToString())
}

private fun calculateDeterminant(matrix: Array<Array<Double>>): Double {
    val lu = decompose(matrix)
    var det = 1.0

    for (i in lu.indices) {
        det *= lu[i][i]
    }

    return det
}

private fun decompose(sourceMatrix: Array<Array<Double>>): Array<Array<Double>> {
    val matrix = sourceMatrix.copyOf()

    for (i in 0..<matrix.size - 1) {
        for (j in i + 1..<matrix.size) {
            val multiplier = matrix[j][i] / matrix[i][i]

            for (k in i..<matrix.size) {
                matrix[j][k] -= matrix[i][k] * multiplier
            }
        }
    }

    return matrix
}

private fun calculateResult(
    conditions: Array<Array<Double>>,
    equality: Array<Double>,
    determinant: Double
): Array<Double> {
    val results = Array(conditions.size) { .0 }

    for (i in conditions.indices) {
        val det = calculateDeterminant(replaceColumn(conditions, equality, i))
        results[i] = det / determinant;
    }

    return results
}

private fun replaceColumn(matrix: Array<Array<Double>>, column: Array<Double>, columnIndex: Int): Array<Array<Double>> {
    val result = matrix.copyOf()

    for (i in result.indices) {
        result[i][columnIndex] = column[i]
    }

    return result
}

fun <T> validateEquationsInput(sourceMatrix: Array<Array<T>>, sourceResult: Array<T>) {
    val size = equations.size;

    for (row in sourceMatrix) {
        if (row.size != size) throw IllegalArgumentException("Incorrect row size")
    }

    if (sourceResult.size != size) throw IllegalArgumentException("Incorrect result vector size")
}

inline fun <reified T> Array<Array<T>>.copyOf(): Array<Array<T>> = Array(this.size) { i -> this[i].copyOf() }
