package ru.ch3oh.matrixCalculator

fun main() {
    println("Доступные действия для матриц(ы):")
    println()
    println("1) Сумма матриц")
    println("2) Разность матриц")
    println("3) Умножение матрицы на число")
    println("4) Умножение матрицы на матрицу")
    println("5) Транспонирование матрицы")
    println("6) Определитель матрицы")
    println("7) Ранг матрицы")
    println("8) Обратная матрица")
    println("9) Решение СЛАУ")
    println()
    print("Выберите действие: ")
    val action: Action = when (readln().toInt()) {
        1 -> Action.SUM
        2 -> Action.SUBTRACTION
        3 -> Action.MULTIPLICATION_BY_NUMBER
        4 -> Action.MULTIPLICATION
        5 -> Action.TRANSPOSITION
        6 -> Action.DETERMINANT
        7 -> Action.RANK
        8 -> Action.INVERSE_MATRIX
        9 -> Action.SOLVE
        else -> Action.MULTIPLICATION_BY_NUMBER
    }
    println()
    print("Введите количество строк первой матрицы: ")
    val firstMatrixRows: Int = readln().toInt()
    print("Введите количество столбцов первой матрицы: ")
    val firstMatrixColumns: Int = readln().toInt()
    val firstMatrix = Matrix(firstMatrixRows, firstMatrixColumns)
    var secondMatrix = Matrix(1,1)
    var number = 1.0
    println()
    println("Введите элементы первой матрицы по порядку.")
    firstMatrix.fill()
    println()
    println("Итоговая первая матрица:")
    println(firstMatrix)
    if (action === Action.MULTIPLICATION_BY_NUMBER) {
        //1 матрица и 1 число
        print("Теперь введите число, на которое надо умножить матрицу: ")
        number = readln().toDouble()
    } else if (action === Action.SOLVE) {
        println("Ввесдите свободные члены: ")
        val secondMatrixLines: Int = firstMatrixRows
        val secondMatrixColumns = 1
        secondMatrix = Matrix(secondMatrixLines, secondMatrixColumns)
        secondMatrix.fill()
        println()
        println("Итоговая вторая матрица:")
        println(secondMatrix)
    } else {
        //2 матрицы
        print("Введите количество строк второй матрицы: ")
        val secondMatrixLines: Int = readln().toInt()
        print("Введите количество столбцов второй матрицы: ")
        val secondMatrixColumns: Int = readln().toInt()
        secondMatrix = Matrix(secondMatrixLines, secondMatrixColumns)
        println("Введите элементы второй матрицы по порядку.")
        secondMatrix.fill()
        println()
        println("Итоговая вторая матрица:")
        println(secondMatrix)

    }
    println()
    println("Итог: ")
    when (action) {
        Action.SUM -> println(firstMatrix + secondMatrix)
        Action.SUBTRACTION -> println(firstMatrix - secondMatrix)
        Action.MULTIPLICATION -> println(firstMatrix * secondMatrix)
        Action.MULTIPLICATION_BY_NUMBER -> println(firstMatrix * number)
        Action.TRANSPOSITION -> println(firstMatrix.transpose())
        Action.DETERMINANT -> println(firstMatrix.determinant())
        Action.INVERSE_MATRIX -> println(firstMatrix.inverse())
        Action.RANK -> println(firstMatrix.rank())
        Action.SOLVE -> println(firstMatrix.solve(secondMatrix))
    }
}