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

    private fun setMines(x: Int, y: Int) {
        var minesSet = 0
        do { line.forEachIndexed { i, l -> l.forEachIndexed { j, _ ->
            if (minesSet < mines && Math.random() < mines / 100.0 && !l[j] && !(x == j && y == i)) {
                l[j] = true
                minesSet++
            } } }
        } while (minesSet < mines)
    }

    private var initialized = false

    private val printedLine = List(height) { CharArray(width) { '.' } }

    private fun printField() {
        val border = "—│${"—".repeat(width * 2 - 1)}│"
        println()
        println(" │${(1..width).joinToString(" ")}│")
        println(border)
        printedLine.forEachIndexed { i, it -> println("${i + 1}│${it.joinToString(" ")}│") }
        println(border)
    }

    private fun exploreCell(x: Int, y: Int) {
                if (!line[y][x]) {
                    var minesAround = 0
                    (y - 1..y + 1).forEach { h -> (x - 1..x + 1).forEach { w ->
                        if (h in line.indices && w in line[y].indices && line[h][w]) minesAround++ } }
                    if (minesAround > 0) printedLine[y][x] = minesAround.toString().single()
                    else if (printedLine[y][x] == '.') {
                        printedLine[y][x] = '/'
                        (y - 1..y + 1).forEach { h -> (x - 1..x + 1).forEach { w ->
                                if (h in line.indices && w in line[y].indices) exploreCell(w, h)
                            } }
                    }
                } else {
                    line.forEachIndexed { i, l -> l.forEachIndexed { j, _ ->
                        if (line[i][j]) printedLine[i][j] = 'X' } }
                }
    }

    private fun markCell(x: Int, y: Int) {
        when (printedLine[y][x]) {
            '.' -> printedLine[y][x] = '*'
            '*' -> printedLine[y][x] = '.'
        }
    }

    private tailrec fun selectCell() {
        print("Set/unset mine marks or claim a cell as free: ")
        val x = scanner.nextInt() - 1
        val y = scanner.nextInt() - 1
        val action = scanner.next()

        if (!initialized) {
            setMines(x, y)
            initialized = true
        }

        if (printedLine[y][x] != '.' && printedLine[y][x] != '*') {
            println("Cell is free!")
            return selectCell()
        }
        when (action) {
            "mine" -> markCell(x, y)
            "free" -> exploreCell(x, y)
        }
    }

    private fun checkMarks(): Boolean = printedLine.mapIndexed { i, em ->
        em.map { it == '*' || it == '.' }.toBooleanArray().contentEquals(line[i]) }.all { it }

    fun start() {
        do {
            printField()
            selectCell()
        } while (!(checkMarks() || printedLine.any { it.contains('X') }) )
        printField()
        print(if (!printedLine.any { it.contains('X') }) "Congratulations! You found all the mines!"
        else "You stepped on a mine and failed!")
    }
}