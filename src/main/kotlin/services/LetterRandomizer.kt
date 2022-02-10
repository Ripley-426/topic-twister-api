package services

class LetterRandomizer {

    fun getRandomLetter(): Char {

        return ('A'..'Z').random()

    }
}