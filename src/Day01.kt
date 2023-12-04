fun main() {
    fun part1(input: List<String>): Int {
        val values = input.map {
            val first = it.find { it.isDigit()}
            val last = it.findLast { it.isDigit()}
            (first.toString() + last.toString()).toInt()
        }
        return values.sum();
    }

    fun digitOrStringNum(line:String) : Sequence<MatchResult> {
        val regex = "[1-9]|one|two|three|four|five|six|seven|eight|nine".toRegex();
        return regex.findAll(line)
    }
    fun stringToNumber (str: String) : Int {
       return when (str){
           "1" -> 1
           "2" -> 2
           "3" -> 3
           "4" -> 4
           "5" -> 5
           "6" -> 6
           "7" -> 7
           "8" -> 8
           "9" -> 9
           "one" -> 1
           "two" -> 2
           "three" -> 3
           "four" -> 4
           "five" -> 5
           "six" -> 6
           "seven" -> 7
           "eight" -> 8
           "nine" -> 9
           else -> 0
       }
    }
    fun part2(input: List<String>): Int {
        val values = input.map { line ->
            val first = digitOrStringNum(line).first().value;
            val last = digitOrStringNum(line).last().value;
            (stringToNumber(first).toString() + stringToNumber(last).toString()).toInt()
        }
        return values.sum();
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    part1(testInput).println()
//    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
