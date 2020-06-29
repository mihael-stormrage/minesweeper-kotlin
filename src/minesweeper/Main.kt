package minesweeper

import java.util.*

val scanner = Scanner(System.`in`)

fun main() {
    val fieldSize = 9

    print("How many mines do you want on the field? ")
    val mines = scanner.nextInt()

    val field = Minesweeper(fieldSize, fieldSize, mines)

    field.printField()
}

class Minesweeper(width: Int = 9, height: Int = 9, val mines: Int = 10) {
    private val line = List(height) { CharArray(width) { '.' } }

    private fun setMines() {
        var minesSet = 0
        do {
            for (i in line.indices)
                for (j in line[i].indices) {
                    if (minesSet < mines && Math.random() < mines / 100.0
                            && line[i][j] != 'X') {
                        line[i][j] = 'X'
                        minesSet++
                    }
                }
        } while (minesSet < mines)
    }

    init {
        setMines()
    }

    fun printField() {
        line.forEach { println(it.joinToString("")) }
    }
}