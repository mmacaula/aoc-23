import kotlin.math.log

fun main() {
    data class Game(val id: String, val winningNumbers: List<Int>, val numbers: List<Int>)

    fun parseGame(line: String): Game {
        val (gameId, rest) = line.split(":")
        val (winningStr, numbersStr) = rest.split("|");
        val winningNumbers = winningStr.trim().split(Regex("\\s+")).map { it.toInt() }
        val numbers = numbersStr.trim().split(Regex("\\s+")).map { it.toInt() }
        return Game(gameId, winningNumbers, numbers)
    }

    fun parse(input: List<String>): List<Game> {
        return input.map { parseGame(it) }
    }

    fun part1(input: List<String>): Int {
        val games = parse(input)
        return games.sumOf { game ->
            val gameWinners = game.numbers.filter {
                game.winningNumbers.contains(it)
            }
            Math.pow(2.0, gameWinners.size.toDouble() - 1).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val games = parse(input)
        val processedGames = games.groupBy { it.id }

        processedGames.keys.forEachIndexed { i, gameId ->
            println("new game: $gameId")
            val gamesToProcess = processedGames[gameId]!!.size;
            println("games to process $gamesToProcess")
            val game = processedGames[gameId]!![0]
            val gameWinners = game.numbers.filter { game.winningNumbers.contains(it) }
            repeat(gameWinners.size) {
                val idx = i + it + 1
                println("Adding game to map: ${games[idx]}")
                val gameToAdd = games[idx]
                repeat(gamesToProcess) {
                    processedGames[gameToAdd.id]!!.addLast(games[idx])
                }
            }
        }
        return processedGames.values.sumOf { it.size }
    }

    val day = "Day04"
    val testInput = readInput("${day}_test")
    val input = readInput(day)
    assert(part1(testInput) == 13)
    assert(part1(input) == 23673)

    part2(testInput).println()
    assert(part2(testInput) == 30)
    println(part2(input))
}
