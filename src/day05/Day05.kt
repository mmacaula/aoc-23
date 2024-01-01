package day05

import println
import readInput

fun main() {
    fun parseSeedsPt1(input: String): List<Long> {
        val (_, seeds) = input.split(":")
        return seeds.trim().split(" ").map { it.toLong() }
    }



    data class Range(val destStart: Long, val sourceStart: Long, val length: Long) {
        fun isInRange(source: Long): Boolean {
            return source >= sourceStart && source < (sourceStart + length)
        }

        fun map(source: Long): Long {
            val diff = source - sourceStart
            return destStart + diff
        }
    }

    class RangeMap(val ranges: List<Range>) {
        fun map(source: Long): Long {
            val range = ranges.find { it.isInRange(source) }
            return range?.map(source) ?: source
        }


    }
    fun buildRangeMap(inputs: List<String>): RangeMap {
        return RangeMap(inputs.map {
            it.split(" ")
                .map { it.toLong() }
        }.map {
            Range(it[0], it[1], it[2])
        })
    }
    data class Mapper(
                      val seedToSoil: RangeMap,
                      val soilToFertilizer: RangeMap,
                      val fertilizerToWater: RangeMap,
                      val waterToLight: RangeMap,
                      val lightToTemp: RangeMap,
                      val tempToHumidity: RangeMap,
                      val humidityToLocation: RangeMap,
    ){
        fun map(source:Long): Long{
            var mapped = source
            mapped = seedToSoil.map(mapped)
            mapped = soilToFertilizer.map(mapped)
            mapped = fertilizerToWater.map(mapped)
            mapped = waterToLight.map(mapped)
            mapped = lightToTemp.map(mapped)
            mapped = tempToHumidity.map(mapped)
            mapped = humidityToLocation .map(mapped)
            return mapped;
        }
    }

    fun parse(input: List<String>): Mapper {
        val seedsToSoilRangeEnd = input.indexOf("soil-to-fertilizer map:")
        val seedToSoil = input.subList(3, seedsToSoilRangeEnd - 1)

        val soilToFertRangeEnd = input.indexOf("fertilizer-to-water map:")
        val soilToFert = input.subList(seedsToSoilRangeEnd + 1, soilToFertRangeEnd - 1)

        val fertToWaterRangeEnd = input.indexOf("water-to-light map:")
        val fertToWater = input.subList(soilToFertRangeEnd + 1, fertToWaterRangeEnd - 1)

        val waterToLightRangeEnd = input.indexOf("light-to-temperature map:")
        val waterToLight = input.subList(fertToWaterRangeEnd + 1, waterToLightRangeEnd - 1)

        val lightToTempRangeEnd = input.indexOf("temperature-to-humidity map:")
        val lightToTemp = input.subList(waterToLightRangeEnd + 1, lightToTempRangeEnd - 1)

        val tempTohumidRangeEnd = input.indexOf("humidity-to-location map:")
        val tempToHumid = input.subList(lightToTempRangeEnd + 1, tempTohumidRangeEnd - 1)

        val humidToLoc = input.subList(tempTohumidRangeEnd + 1, input.size)
        val mapper = Mapper( buildRangeMap(seedToSoil), buildRangeMap(soilToFert), buildRangeMap(fertToWater), buildRangeMap(waterToLight),
        buildRangeMap(lightToTemp), buildRangeMap(tempToHumid), buildRangeMap(humidToLoc))
        return mapper;
    }

    fun part1(input: List<String>): Long {
        val seeds = parseSeedsPt1(input[0]);
        val mapper = parse(input)
        return seeds.map{ mapper.map(it)}.min()
    }

    fun parseSeedsPt2(input: String): Sequence<Long> {
        val (_, seedPairs) = input.split(":")
        return seedPairs.trim().split(" ").chunked(2)
            .asSequence()
            .flatMap{

                val (start, length) = it.map{it.toLong()}
                generateSequence(start){
                    if(it >= start + length-1){
                        null
                    } else {
                        it + 1
                    }
                }
            }
    }

    fun part2(input: List<String>): Long {
        val seeds = parseSeedsPt2(input[0]);
        val mapper = parse(input)
        return seeds.map{ mapper.map(it)}.min()
    }

    val day = "Day05"
    val testInput = readInput("${day}_test")
    val input = readInput(day)
    assert(part1(testInput) == 35L)
    assert(part1(input) == 31599214L)

    assert(part2(testInput) == 46L)
    part2(input).println()
}
