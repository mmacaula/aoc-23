package day08

import println
import readInput
enum class Direction() {
    R,
    L,
}
fun main() {

    data class MapEntry(val location: String, val left:String, val right: String)
    data class DesertMap(val directions: String, val entries: Map<String,MapEntry>)
    fun parseInput(input: List<String>): DesertMap{
        val directionsStr = input[0]
        val map = input.subList(2, input.size)
        val entries = map.map{ line ->
            val location = line.substring(0, 3)
            MapEntry(location, line.substring(7, 10) ,line.substring(12, 15))
        }
        return DesertMap(directionsStr, entries.associateBy { it.location })

    }
    fun part1(input: List<String>): Int {
        val desertMap = parseInput(input)
        var current = desertMap.entries["AAA"]!!;
        var directionIter = 0
        while(current.location != "ZZZ") {
            val dir = desertMap.directions[directionIter++ % desertMap.directions.length]
            val direction = Direction.valueOf(dir.toString())
            println("Going ${direction}, current: ${current.location}, left is ${current.left}, right is ${current.right}")
            if(direction == Direction.R){
                current = desertMap.entries[current.right]!!
            }else current = desertMap.entries[current.left]!!

        }
        println("done: $current:  $directionIter")
        println(desertMap)
        return directionIter
    }

    fun allEndsWithZ(startingNodes: List<MapEntry>) = startingNodes.all { it.location.endsWith("Z") }

    fun part2(input: List<String>): Long {
        val desertMap = parseInput(input)
        val startingNodes = desertMap.entries.filter { it.key.endsWith("A") }.map{it.value}
        println("Starting length ${startingNodes}")
        var currentLocations = startingNodes.toList()
        var directionIter = 0L
        while(! allEndsWithZ(currentLocations)) {
            val dir = desertMap.directions[(directionIter++ % desertMap.directions.length).toInt()]
            val direction = Direction.valueOf(dir.toString())
            currentLocations = currentLocations.map{
                if(direction == Direction.R){
                     desertMap.entries[it.right]!!
                }else desertMap.entries[it.left]!!
            }
            if(directionIter % 1_000_000 == 0L){
                println(directionIter)
            }
        }

        return directionIter
    }

    val day = "Day08"
    val testInput = readInput("${day.lowercase()}/${day}_test3")
    val input = readInput("${day.lowercase()}/$day")
//    part1(testInput).println()
//    part1(input).println()
//    part2(testInput).println()
    part2(input).println()
}
