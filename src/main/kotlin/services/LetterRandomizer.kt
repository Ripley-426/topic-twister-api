package services

class LetterRandomizer {

    fun getRandomLetter(): String {

        return ('A'..'Z').random().toString()

    }
}