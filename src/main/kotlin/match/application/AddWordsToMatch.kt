package com.example.match.application

import com.example.match.infrastructure.DBMatchLoader

class AddWordsToMatch {

    private val matchLoader = DBMatchLoader()

    fun addWords(playerID: Int, matchID: Int, words: String) {

        var matchToPlay = matchLoader.loadMatch(matchID)

        if (playerID != matchToPlay.currentTurnPlayerID()) { return }

        val upperWords = words.uppercase()

        var wordsList = upperWords.split(",").toMutableList()
        if (wordsList.size < 6){
            completeListWithEmpty(wordsList)
        }
        matchToPlay.addWords(wordsList)

        DBMatchLoader().updateMatch(matchToPlay)
    }

    private fun completeListWithEmpty(wordsList: MutableList<String>) {
        for (i in wordsList.size..4) {
            wordsList.add("")
        }
    }

}