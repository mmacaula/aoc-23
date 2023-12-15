enum class Color() {
    RED,
    BLUE,
    GREEN
}
val txtToColor = mapOf(
    "red" to Color.RED,
    "blue" to Color.BLUE,
    "green" to Color.GREEN
)

fun main(){


    data class Pull(val number: Int, val color: Color)
    data class Game(val id: Int, val pulls: List<Pull>)

    fun parseGames(input: List<String>): List<Game> {
        val games = input.map { game ->
            val (gameStr, allRolls) = game.split(':')
            val (_, id) = gameStr.split(" ")
            val pulls = allRolls.trim().split(";").flatMap { pulls ->
                pulls.split(",").map {
                    val (num, color) = it.trim().split(" ")
                    Pull(num.toInt(), txtToColor[color]!!)
                }
            }
            Game(id.toInt(), pulls)
        }
        return games
    }

    fun part1(input: List<String>): Int {
        val games = parseGames(input)

        return games.sumOf {game ->
            if(
                (game.pulls.filter { it.color == Color.RED }.any { it.number > 12})||
                (game.pulls.filter { it.color == Color.BLUE }.any { it.number > 14})||
                (game.pulls.filter { it.color == Color.GREEN }.any { it.number > 13})
            ){
                0
            }else{
                game.id
            }
        }
    }

    fun part2(input: List<String>): Int {
        val games = parseGames(input)
        return games.sumOf {game ->
            val maxRed = game.pulls.filter { it.color == Color.RED }.maxOf { it.number }
            val maxBlue = game.pulls.filter { it.color == Color.BLUE }.maxOf { it.number }
            val maxGreen = game.pulls.filter { it.color == Color.GREEN }.maxOf { it.number }
            maxRed * maxBlue * maxGreen;
        }
    }

    val input = readInput("Day02")
    val testInput = readInput("Day02_test")
//    println(part1(input))
    println(part2(input))
}
