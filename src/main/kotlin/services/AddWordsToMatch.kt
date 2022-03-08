package com.example.services

import com.example.model.Match
import services.LetterRandomizer

class AddWordsToMatch() {

    private val matchLoader = DBMatchLoader()

    fun addWords(playerID: Int, matchID: Int, words: String, secondWords:String = "") {


        var matchToPlay = matchLoader.loadMatch(matchID)

        if (playerID != matchToPlay.currentTurnPlayerID()) { return }

        val upperWords = words.uppercase()

        var wordsList = upperWords.split(",").toMutableList()
        if (wordsList.size < 6){for (i in wordsList.size..4) {wordsList.add("")}}
        matchToPlay.addWords(wordsList)

        DBMatchLoader().updateMatch(matchToPlay)
    }

}