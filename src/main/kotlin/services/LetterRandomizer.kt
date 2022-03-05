package services

import com.example.interfaces.ILetterRandomizer

class LetterRandomizer: ILetterRandomizer {

    var letterList : MutableList<Char>  = mutableListOf()

    override fun getRandomLetter(): Char {
        var letter = ('A'..'Z').random()
        while (letterList.contains(letter)){
            letter = ('A'..'Z').random()
        }
        letterList.add(letter)

        return letter
    }
}