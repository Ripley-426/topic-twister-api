package services

import com.example.interfaces.ILetterRandomizer

class LetterRandomizer: ILetterRandomizer {

    override fun getRandomLetter(): Char {

        return ('A'..'Z').random()

    }
}