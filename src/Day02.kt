import kotlin.math.log

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

    fun part1(input: List<String>): Int {
        val games = input.map{game ->
            val (gameStr, allRolls) = game.split(':')
            val (_, id) = gameStr.split(" ")
            val pulls = allRolls.trim().split(";").flatMap {pulls ->
                pulls.split(",").map{
                    val (num, color) = it.trim().split(" ")
                    Pull(num.toInt(), txtToColor[color]!!)
                }
            }
            Game(id.toInt(), pulls)
        }

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

    val input = readInput("Day02")
    println(part1(input))
}
