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

    fun part2(input: List<String>): Int {
        return 0
    }

    val day = "Day03"
    val testInput = readInput("${day}_test")
    val input = readInput(day)
    part1(testInput).println()
    part1(input).println()
//    part2(input).println()
}
