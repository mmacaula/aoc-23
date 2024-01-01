package day07

import println
import readInput

enum class Type(val rank: Int) {
    FIVE_KIND(7),
    FOUR_KIND(6),
    FULL_HOUSE(5),
    THREE_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    ONE_HIGH(1);

    fun increase(by: Int): Type {
        if(by == 0){
            return this
        }
        val isFullHouseRange = FULL_HOUSE.rank in this.rank ..this.rank + by
        val isTwoPairRange = TWO_PAIR.rank in this.rank .. this.rank + by
        if(this == TWO_PAIR && by == 1){
            return FULL_HOUSE
        }
        if(this == ONE_PAIR && by == 1){
            return THREE_KIND
        }
        if(this == ONE_PAIR && by == 2){
            return FOUR_KIND
        }
        if(this == ONE_PAIR && by == 3){
            return FIVE_KIND
        }
        if(this == ONE_HIGH && by == 4){
            return FIVE_KIND
        }
        if(this == ONE_HIGH && by == 3){
            return FOUR_KIND
        }
        if(this == FULL_HOUSE && by == 1){
            return FOUR_KIND
        }

        val betterBy = if(isFullHouseRange || isTwoPairRange) by+1 else by
        val increased = Type.entries.find {
            it.rank == (this.rank + betterBy)
        }?: FIVE_KIND
        return increased

    }
}

fun main() {
    val rankingMap = mutableMapOf(
        '2' to 2,
        '3' to 3,
        '4' to 4,
        '5' to 5,
        '6' to 6,
        '7' to 7,
        '8' to 8,
        '9' to 9,
        'T' to 10,
        'J' to 11,
        'Q' to 12,
        'K' to 13,
        'A' to 14,
        'X' to 0,
        'Y' to 0,
        'Z' to 0,
        'W' to 0,

    )


    class Cards(val cards: String) {
        var type: Type
        var type2: Type

        fun parseType1(cards: String): Type {
            val grouped = cards.groupBy { it }
            val maxNum = grouped.maxOf { it.value.size }
            when (maxNum) {
                5 -> return Type.FIVE_KIND
                4 -> return Type.FOUR_KIND
                1 -> return Type.ONE_HIGH
            }
            if (grouped.filter { it.value.size == 2 }.size == 1 &&
                grouped.filter { it.value.size == 3 }.size == 1
            ) {
                return Type.FULL_HOUSE
            }
            if (grouped.filter { it.value.size == 3 }.size == 1) {
                return Type.THREE_KIND
            }
            if (grouped.filter { it.value.size == 2 }.size == 2) {
                return Type.TWO_PAIR
            }
            if (grouped.filter { it.value.size == 2 }.size == 1) {
                return Type.ONE_PAIR
            }
            assert(false) { "invalid logic" }
            return Type.ONE_HIGH
        }

        fun parseType2(cards: String): Type {
            val grouped = cards.groupBy { it }
            val type = if(grouped.filter { it.key  == 'J'}.size > 0){
                val replaced = cards.replace("J","")
                println("replaced: $replaced")
                if(replaced == ""){
                    return Type.FIVE_KIND
                }
                parseType1(replaced)
            } else parseType1(cards)
            println("type is $type")
            val numJs = grouped['J']?.size ?: 0

            val uppedType = type.increase(numJs)
            if(numJs != 0){
                println("Num Js: $numJs $cards" )
                println("Was $type and now is $uppedType")
            }
            return uppedType
        }

        init {
            this.type = parseType1(cards)
            this.type2 = parseType2(cards)
        }
    }

    data class Hand(val cards: Cards, val bid: Int)

    val HandCardsComparator = Comparator<Hand> { cards1, cards2 ->
        val ranks1 = cards1.cards.cards.map { rankingMap[it] }
        val ranks2 = cards2.cards.cards.map { rankingMap[it] }
        ranks1.forEachIndexed { index, i ->
            val compared = ranks2[index]!!.compareTo(i!!)
            if (compared != 0) {
                return@Comparator compared
            }
        }
        return@Comparator 0
    }

    fun parse(input: List<String>): List<Hand> {
        val hands: List<Hand> = input.map {
            val (cardsStr, bidStr) = it.split(" ")
            Hand(Cards(cardsStr), bidStr.toInt())
        }
        return hands;

    }

    fun part1(input: List<String>): Int {
        val hands = parse(input)

        val sorted = hands.sortedWith(compareBy<Hand>({ it.cards.type.rank }).then(HandCardsComparator.reversed()))
            .mapIndexed { idx, it ->
                println("${it.bid} ${idx + 1}  ${it.cards.type}: ${it.cards.cards}")
                (idx + 1) * it.bid
            }.sum()
        return sorted
    }

    fun part2(input: List<String>): Int {
        rankingMap['J'] = 1;
        val hands = parse(input)

        val sorted = hands.sortedWith(compareBy<Hand> { it.cards.type2.rank }.then(HandCardsComparator.reversed()))
            .mapIndexed { idx, it ->
                println("${it.bid} ${idx + 1}  ${it.cards.type2}: ${it.cards.cards}")
                (idx + 1) * it.bid
            }.sum()
        return sorted
    }

    val day = "Day07"
    val testInput = readInput("${day}_test")
    val input = readInput(day)
    //    part1(testInput).println()
//    assert(part1(testInput ) == 6440)
//    part1(input).println()
//    part2(testInput).println()
    part2(input).println()
    assert(Type.ONE_HIGH.increase(1) == Type.ONE_PAIR)
    assert(Type.ONE_HIGH.increase(2) == Type.THREE_KIND)
    println(Type.ONE_HIGH.increase(3))
    assert(Type.ONE_HIGH.increase(3) == Type.FOUR_KIND)
    assert(Type.ONE_HIGH.increase(4) == Type.FIVE_KIND)
    assert(Type.ONE_PAIR.increase(1) == Type.THREE_KIND)
    assert(Type.ONE_PAIR.increase(2) == Type.FOUR_KIND)
    assert(Type.ONE_PAIR.increase(3) == Type.FIVE_KIND)
    assert(Type.TWO_PAIR.increase(1) == Type.FULL_HOUSE)
    assert(Type.TWO_PAIR.increase(2) == Type.FOUR_KIND)
    assert(Type.TWO_PAIR.increase(3) == Type.FIVE_KIND)
    assert(Type.FULL_HOUSE.increase(1) == Type.FOUR_KIND)
    assert(Type.FULL_HOUSE.increase(2) == Type.FIVE_KIND)
    assert(Type.THREE_KIND.increase(1) == Type.FOUR_KIND)
    assert(Type.THREE_KIND.increase(2) == Type.FIVE_KIND)
    assert(Type.FOUR_KIND.increase(1) == Type.FIVE_KIND)
    assert(Cards("JJ6TA").type2 == Type.THREE_KIND)
    assert(Cards("6A4A6").type2 == Type.TWO_PAIR)
}
