package day03

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        fun shouldCount(char:Char):Boolean{
            return char != '.' && !char.isDigit()
        }
        val chars = input.map{ it.toCharArray()}

        var numbers = mutableListOf<String>();
        chars.forEachIndexed { row, lineOfChars ->
            var number =  ""
            var countIt = false
            lineOfChars.forEachIndexed { column, char ->
                if (char.isDigit()){
                    number += char
                }else {
                    if(countIt && number != ""){
                        numbers.add(number)
                        countIt = false
                    }
                    number = ""
                    return@forEachIndexed
                }
                if(row > 0){ // check out top
                    val top = chars[row-1][column]
                    if(shouldCount(top)){
                        countIt = true
                    }
                    if(column > 0){ // top left
                        val topLeft = chars[row-1][column-1]
                        if(shouldCount(topLeft)){
                            countIt = true
                        }
                    }
                    if(column != lineOfChars.size -1) { // top right
                        val topRight: Char = chars[row - 1][column + 1]
                        if (shouldCount(topRight)) {
                            countIt = true
                        }
                    }
                }
                if(column != lineOfChars.size -1 ) {  // check out right side
                    val right = chars[row][column+1]
                    if(shouldCount(right)){
                        countIt = true
                    }
                }
                //check out left side
                if(column > 0){
                    val left = chars[row][column -1]
                    if(shouldCount(left)){
                        countIt = true
                    }
                }
                // check our bottom
                if(row != chars.size - 1){
                    val bottom = chars[row +1][column]
                    if(shouldCount(bottom)){
                        countIt = true
                    }
                    // bottom left
                    if(column > 0){
                        val bottomLeft = chars[row+1][column-1]
                        if(shouldCount(bottomLeft)){
                            countIt = true
                        }
                    }
                    if(column != lineOfChars.size -1 ) {  // check out bottom right side
                        val bottomRight = chars[row + 1][column+1]
                        if(shouldCount(bottomRight)){
                            countIt = true
                        }
                    }
                }
                if(column == lineOfChars.size -1 && countIt){
                    numbers.add(number)
                    countIt = false
                }
            }
        }
        println("numbers: ${numbers}")

        return numbers.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Long {
        fun shouldCount(char:Char):Boolean{
            return char == '*'
        }
        val chars = input.map{ it.toCharArray()}
        data class GearPiece(val number: Int, val gearLocation :Pair<Int,Int>)

        var gears = mutableListOf<GearPiece>();
        var gearLocation: Pair<Int,Int>? = null;
        chars.forEachIndexed { row, lineOfChars ->
            var number =  ""
            var countIt = false

            lineOfChars.forEachIndexed { column, char ->
                if (char.isDigit()){
                    number += char
                }else {
                    if(countIt && number != ""){
                        gears.add(GearPiece(number.toInt(), gearLocation!!))
                        countIt = false
                        gearLocation = null;
                    }
                    number = ""
                    return@forEachIndexed
                }
                if(row > 0){ // check out top
                    val top = chars[row-1][column]
                    if(shouldCount(top)){
                        countIt = true
                        gearLocation = Pair(row-1,column)
                    }
                    if(column > 0){ // top left
                        val topLeft = chars[row-1][column-1]
                        if(shouldCount(topLeft)){
                            countIt = true
                            gearLocation = Pair(row-1,column-1)
                        }
                    }
                    if(column != lineOfChars.size -1) { // top right
                        val topRight: Char = chars[row - 1][column + 1]
                        if (shouldCount(topRight)) {
                            countIt = true
                            gearLocation = Pair(row-1,column+1)
                        }
                    }
                }
                if(column != lineOfChars.size -1 ) {  // check out right side
                    val right = chars[row][column+1]
                    if(shouldCount(right)){
                        countIt = true
                        gearLocation = Pair(row,column+1)
                    }
                }
                //check out left side
                if(column > 0){
                    val left = chars[row][column -1]
                    if(shouldCount(left)){
                        countIt = true
                        gearLocation = Pair(row,column-1)
                    }
                }
                // check out bottom
                if(row != chars.size - 1){
                    val bottom = chars[row +1][column]
                    if(shouldCount(bottom)){
                        countIt = true
                        gearLocation = Pair(row+1,column)
                    }
                    // bottom left
                    if(column > 0){
                        val bottomLeft = chars[row+1][column-1]
                        if(shouldCount(bottomLeft)){
                            countIt = true
                            gearLocation = Pair(row+1,column-1)
                        }
                    }
                    if(column != lineOfChars.size -1 ) {  // check out bottom right side
                        val bottomRight = chars[row + 1][column+1]
                        if(shouldCount(bottomRight)){
                            countIt = true
                            gearLocation = Pair(row+1,column+1)
                        }
                    }
                }
                if(column == lineOfChars.size -1 && countIt){  // far right
                    gears.add(GearPiece(number.toInt(), gearLocation!!))
                    countIt = false
                    gearLocation = null;
                }
            }
        }
//        println("gears: ${gears}")
        val sum : Long= gears.sumOf { gear ->
            val matching = gears.find { it !== gear && it.gearLocation == gear.gearLocation }
            if( matching != null){
                (matching.number * gear.number ).toLong()
            }else 0
        } / 2
        val gearsWithMatch = gears.filter { gear ->
            val matching = gears.find { it !== gear && it.gearLocation == gear.gearLocation }
            matching != null
        }
        val gearLocations = gearsWithMatch.map { gear ->
            gear.gearLocation
        }.toSet()

        var runningSum = 0
        val gearCentric = gearLocations.map{ location ->
            val loc = Pair(location, gears.filter{ it.gearLocation == location}.map{it.number}.sorted())
            loc
        }.sortedBy { it.second[0] }.map {
            runningSum += it.second.sum()
            println("$it: sum ${runningSum}")
            it
        }

//        println("with match: $gearCentric")
        println("with match length: ${gearsWithMatch.size}")
        println("other sum: "+ gearCentric.sumOf{ it.second[0] * it.second[1]});



        return sum;
    }

    val day = "Day03"
    val testInput = readInput("${day}_test")
    val input = readInput(day)
//    part1(testInput).println()
//    part1(input).println()
    part2(testInput).println()

    // 83245878  current answer
    // 83279367
    part2(input).println()
}
