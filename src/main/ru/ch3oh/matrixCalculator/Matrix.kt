package ru.ch3oh.matrixCalculator

import java.text.DecimalFormat
import kotlin.math.pow

class Matrix (matrix: Array<Array<Double>>) {
    constructor(lines: Int, columns : Int) : this(matrix = Array(lines) {Array(columns) {0.0} })
    private var _matrix = matrix
    private val format = DecimalFormat("#.#####")
    private var indices = _matrix.indices
    operator fun plus (b: Matrix): Matrix {
        val result = Matrix(this.getRows(), this.getColumns())
        for (i in _matrix.indices) {
            for (j in this[0].indices) {
                result[i][j] = this[i][j] + b[i][j]
            }
        }
        return result
    }
    fun fill() {
        for (i in this.indices) {
            for (j in this[0].indices) {
                print((i + 1).toString() + ";" + (j + 1) + ": ")
                this[i][j] = readln().toDouble()
            }
        }
    }
    operator fun minus (b: Matrix): Matrix {
        val result = Matrix(this.getRows(), this.getColumns())
        for (i in _matrix.indices) {
            for (j in this[0].indices) {
                result[i][j] = this[i][j] - b[i][j]
            }
        }
        return result
    }
    operator fun times (b: Double): Matrix {
        val result = Matrix(this.getRows(), this.getColumns())
        for (i in _matrix.indices) {
            for (j in this[0].indices) {
                result[i][j] = this[i][j] * b
            }
        }
        return result
    }
    operator fun times (b: Int): Matrix {
        val result = Matrix(this.getRows(), this.getColumns())
        for (i in _matrix.indices) {
            for (j in this[0].indices) {
                result[i][j] = this[i][j] * b
            }
        }
        return result
    }
    operator fun times (b: Matrix): Matrix {
        if (this.getColumns() != b.getRows()) {
            throw ArithmeticException("Mismatched matrix sizes")
        }
        val result = Matrix(this.getRows(), b.getColumns())
        for (i in this.indices) {
            for (j in b[0].indices) {
                var cell = 0.0
                for (k in b.indices) {
                    cell += this[i][k] * b[k][j]
                }
                result[i][j] = cell
            }
        }
        return result
    }
    operator fun get (i: Int): Array<Double> {
        return _matrix[i]
    }
    fun getColumns(): Int {
        return _matrix[0].size
    }
    fun getRows(): Int {
        return _matrix.size
    }
    fun transpose(): Matrix {
        val result = Matrix(this.getRows(), this.getColumns())
        for (i in this.indices){
            for (j in this[0].indices) {
                result[i][j] = this[j][i]
            }
        }
        return result
    }
    fun determinant(): Double {
        var result = 0.0
        if (getRows() != getColumns()) {
            throw ArithmeticException("Not a square matrix")
        }
        if (this.getRows() == 1) {
            result = this[0][0]
        } else if (this.getRows() == 2) {
            result = this[0][0] * this[1][1] - this[0][1] * this[1][0]
        } else {
            for (i in this[0].indices) {
                result += this[0][i] * complement(0, i)
            }
        }
        return result
    }
    fun minor(i: Int, j: Int): Double {
        val result: Double
        val tempMatrix = Matrix(this.getRows() - 1, this.getColumns() - 1)
        var flagRow = 0
        var flagColumn: Int
        for (k in 0 until tempMatrix.getRows()) {
            flagColumn = 0
            for (l in 0 until tempMatrix.getColumns()) {
                if (l == j) {
                    flagColumn = 1
                }
                if (k == i) {
                    flagRow = 1
                }
                tempMatrix[k][l] = this[k + flagRow][l + flagColumn]
            }
        }
        result = tempMatrix.determinant()
        return result
    }
    fun complement(i: Int, j: Int): Double{
        return (-1.0).pow(i+j+2)*minor(i,j)
    }
    fun inverse(): Matrix {
        val determinant = this.determinant()
        if (determinant == 0.0) {
            throw ArithmeticException("Determinant can't be 0")
        }
        if (this.getRows() != this.getColumns()) {
            throw ArithmeticException("Not a square matrix")
        }
        val compMatrix = Matrix(this.getRows(), this.getColumns())
        for (i in 0 until this.getRows()) {
            for (j in 0 until this.getColumns()) {
                compMatrix[i][j] = complement(i, j)
            }
        }
        return compMatrix.transpose() * (1 / determinant)
    }
    fun swap(row1: Int, row2: Int, column: Int){
        for (i in 0..column) {
            val temp: Double = this[row1][i]
            this[row1][i] = this[row2][i]
            this[row2][i] = temp
        }
    }
    fun rank(): Int{
        var rank: Int = this.getRows()
        val tempMatrix: Matrix = this
        for (k in this.indices){
            var row = 0
            while (row < rank) {
                if (tempMatrix[row][row] != 0.0) {
                    for (col in this[0].indices) {
                        if (col != row) {
                            val multi: Double = tempMatrix[col][row] / tempMatrix[row][row]
                            for (i in 0 until rank) {
                                tempMatrix[col][i] = tempMatrix[col][i] - multi * tempMatrix[row][i]
                            }
                        }
                    }
                } else {
                    var reduce = true
                    for (i in row + 1 until this.getRows()) {
                        if (tempMatrix[i][row] != 0.0) {
                            swap(row, i, rank)
                            reduce = false
                            break
                        }
                    }
                    if (reduce) {
                        rank--
                        for (i in 0 until this.getRows()) {
                            tempMatrix[i][row] = tempMatrix[i][rank]
                        }
                        row--
                    }
                }
                row++
            }
        }
        return rank
    }
    fun solve (b: Matrix): Matrix {
        if (this.determinant() == 0.0) {
            throw ArithmeticException("Determinant is zero")
        }
        val inv = this.inverse()
        return inv * b
    }
    override fun toString(): String{
        var result = ""
        for (doubles in _matrix) {
            for (i in doubles) {
                result += format.format(i) + " "
            }
            result += "\n"
        }
        return result
    }
}