package minesweeper

import java.util.*

val scanner = Scanner(System.`in`)

fun main() {
    val fieldSize = 9

    print("How many mines do you want on the field? ")
    val mines = scanner.nextInt()

    val field = Minesweeper(fieldSize, fieldSize, mines)

    field.openCells()
    field.printField()
}

class Minesweeper(width: Int = 9, height: Int = 9, val mines: Int = 10) {
    private val line = List(height) { CharArray(width) { '.' } }

    private fun setMines() {
        var minesSet = 0
        do { line.forEach { i -> i.indices.forEach { j ->
                if (minesSet < mines && Math.random() < mines / 100.0 && i[j] != 'X') {
                    i[j] = 'X'
                    minesSet++
                } } }
        } while (minesSet < mines)
    }

    init {
        setMines()
    }

    fun printField() = line.forEach { println(it.joinToString("")) }

    fun openCells() {
        for (i in line.indices)
            for (j in line[i].indices) {
                if (line[i][j] != 'X') {
                    var minesAround = 0
                    (i - 1..i + 1).forEach { h -> (j - 1..j + 1).forEach { w ->
                        if (h in line.indices && w in line[i].indices && line[h][w] == 'X') minesAround++ } }
                    if (minesAround > 0) line[i][j] = minesAround.toString().single()
                }
            }
    }
}