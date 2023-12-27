enum class Type(val rank: Int) {
    FIVE_KIND(7),
    FOUR_KIND(6),
    FULL_HOUSE(5),
    THREE_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    ONE_HIGH(1),
}
fun main() {
    val rankingMap = mapOf(
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
    )


    class Cards(val cards: String){
        var type: Type

        fun parseType(cards: String): Type{
            val grouped = cards.groupBy { it }
            val maxNum = grouped.maxOf { it.value.size }
            when (maxNum){
                5 -> return Type.FIVE_KIND
                4 -> return Type.FOUR_KIND
                1 -> return Type.ONE_HIGH
            }
            if(grouped.filter { it.value.size == 2 }.size == 1 &&
                grouped.filter{it.value.size ==3 }.size == 1) {
                return Type.FULL_HOUSE
            }
            if(grouped.filter { it.value.size == 3 }.size == 1) {
                return Type.THREE_KIND
            }
            if(grouped.filter { it.value.size == 2 }.size == 2) {
                return Type.TWO_PAIR
            }
            if(grouped.filter { it.value.size == 2 }.size == 1) {
                return Type.ONE_PAIR
            }
            assert(false) { "invalid logic" }
            return Type.ONE_HIGH
        }
        init {
            this.type = parseType(cards)
        }
    }
    val cardsComparator = Comparator<Cards> { cards1, cards2 ->
        val ranks1 = cards1.cards.map{rankingMap[it]}
        val ranks2 = cards2.cards.map{rankingMap[it]}
        ranks1.forEachIndexed { index, i ->
            val compared = ranks2[index]!!.compareTo(i!!)
            if( compared != 0){
                return@Comparator compared
            }
        }
        return@Comparator 0
    }
    data class Hand(val cards: Cards, val bid: Int)
    val HandCardsComparator = Comparator<Hand> { cards1, cards2 ->
        val ranks1 = cards1.cards.cards.map{rankingMap[it]}
        val ranks2 = cards2.cards.cards.map{rankingMap[it]}
        ranks1.forEachIndexed { index, i ->
            val compared = ranks2[index]!!.compareTo(i!!)
            if( compared != 0){
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

        val sorted = hands.sortedWith( compareBy<Hand>({it.cards.type.rank}).then(HandCardsComparator.reversed()))
            .mapIndexed { idx, it ->
                println("${it.bid} ${idx+1}  ${it.cards.type}: ${it.cards.cards}")
                (idx +1 )* it.bid
            }.sum()
        return sorted
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val day = "Day07"
    val testInput = readInput("${day}_test")
    val input = readInput(day)
    part1(testInput).println()
    assert(part1(testInput ) == 6440)
    part1(input).println()
//    part2(testInput).println()
//    part2(input).println()
}
