fun main() {
    data class Race(val time: Long, val distance: Long){
        fun calcDistance(timePressed: Long): Long{
            assert(timePressed in 0..time)
            val speed = timePressed
            val timeToTravel = time - timePressed;
            return timePressed * timeToTravel
        }
        fun numWaysToWin(): Long {
            return 0.rangeTo(time).filter { calcDistance(it) > distance }.count().toLong()
        }
    }
    fun parsePt1(input: List<String>): List<Race> {
        val times = input[0].split(":")[1].trim().split(Regex("\\s+")).map{ it.toLong()}
        val distances = input[1].split(":")[1].trim().split(Regex("\\s+")).map{ it.toLong()}
        return times.zip(distances).map{ Race(it.first, it.second)}
    }
    fun part1(input: List<String>): Long {
        val races = parsePt1(input)
        return races.map{ it.numWaysToWin() }.reduce(Long::times)
    }

    fun parsePt2(input: List<String>): Race {
        val time = input[0].split(":")[1].trim().split(Regex("\\s+")).joinToString("").toLong()
        val distance = input[1].split(":")[1].trim().split(Regex("\\s+")).joinToString("").toLong()
        return Race(time, distance)
    }
    fun part2(input: List<String>): Long {
        val race = parsePt2(input)
        return race.numWaysToWin()
    }

    val day = "Day06"
    val testInput = readInput("${day}_test")
    val input = readInput(day)
    assert(part1(testInput) == 288L)
    assert(part1(input) == 211904L)

    assert(part2(testInput) ==71503L )
    println(part2(input));


}
