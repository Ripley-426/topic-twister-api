package com.example.services

import com.example.model.Match
import services.LetterRandomizer

class AddWordsToMatch() {

    private val matchLoader = DBMatchLoader()

    fun addWords(playerID: Int, matchID: Int, words: String, secondWords:String = "") {


        var matchToPlay = matchLoader.loadMatch(matchID)

        if (playerID != matchToPlay.currentTurnPlayerID()) { return }

        var wordsList = words.split(",").toMutableList()
        matchToPlay.addWords(wordsList)

        if (secondWords != "") {
            var secondWordsList = secondWords.split(",").toMutableList()
            matchToPlay.addWords(secondWordsList)
        }

        DBMatchLoader().saveMatch(matchToPlay)
    }

}