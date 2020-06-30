package minesweeper

import java.util.*

val scanner = Scanner(System.`in`)

fun main() {
    val fieldSize = 9

    print("How many mines do you want on the field? ")
    val mines = scanner.nextInt()

    val field = Minesweeper(fieldSize, fieldSize, mines)
    field.start()
}

class Minesweeper(private val width: Int = 9, height: Int = 9, val mines: Int = 10) {
    private val line = List(height) { BooleanArray(width) { false } }

    private fun setMines() {
        var minesSet = 0
        do { line.forEach { i -> i.indices.forEach { j ->
                if (minesSet < mines && Math.random() < mines / 100.0 && !i[j]) {
                    i[j] = true
                    minesSet++
                } } }
        } while (minesSet < mines)
    }

    init {
        setMines()
    }

    private val printedLine = List(height) { CharArray(width) { '.' } }

    private fun printField() {
        val border = "—│${"—".repeat(width)}│"
        println()
        println(" │${(1..width).joinToString("")}│")
        println(border)
        printedLine.forEachIndexed { i, it -> println("${i + 1}│${it.joinToString("")}│") }
        println(border)
    }

    private fun openCells() {
        for (i in line.indices)
            for (j in line[i].indices) {
                if (!line[i][j]) {
                    var minesAround = 0
                    (i - 1..i + 1).forEach { h -> (j - 1..j + 1).forEach { w ->
                        if (h in line.indices && w in line[i].indices && line[h][w]) minesAround++ } }
                    if (minesAround > 0) printedLine[i][j] = minesAround.toString().single()
                }
            }
    }

    private tailrec fun markCell() {
        print("Set/delete mines marks (x and y coordinates): ")
        val x = scanner.nextInt() - 1
        val y = scanner.nextInt() - 1
        when (printedLine[y][x]) {
            '.' -> printedLine[y][x] = '*'
            '*' -> printedLine[y][x] = '.'
            else -> {
                println("There is a number here!")
                return markCell()
            }
        }
    }

    private fun checkMarks(): Boolean = printedLine.mapIndexed { i, em -> em.map { it =='*' }
            .toBooleanArray().contentEquals(line[i]) }.all { it }

    fun start() {
        openCells()
        do {
            printField()
            markCell()
        } while (!checkMarks())
        println("Congratulations! You found all the mines!")
    }
}