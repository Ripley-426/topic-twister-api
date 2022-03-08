package com.example.match.dto

import com.example.match.domain.enumClasses.RoundWinner
import com.example.match.domain.enumClasses.Turn
import com.example.letterRandomizer.ILetterRandomizer
import com.example.topic.domain.ITopicRandomizer
import com.example.wordValidator.domain.IWordValidator
import com.example.match.domain.Round

class LoadedRound(
    roundNumber: Int,
    topicRandomizerDependency: ITopicRandomizer,
    letterRandomizerDependency: ILetterRandomizer,
    wordValidatorDependency: IWordValidator,
    loadedLetter:Char,
    loadedTopics:List<String>,
    loadedPlayerAWords:List<String>? = null,
    loadedPlayerBWords:List<String>? = null,
    loadedPlayerAWordsValidation:List<Boolean>,
    loadedPlayerBWordsValidation:List<Boolean>,
    loadedTurn: Int,
    loadedWinner: Int,
) : Round(roundNumber, topicRandomizerDependency, letterRandomizerDependency, wordValidatorDependency) {

    init {

        letter = loadedLetter
        topics = loadedTopics
        playerAWords = loadedPlayerAWords?.toMutableList() ?: mutableListOf()
        playerBWords = loadedPlayerBWords?.toMutableList() ?: mutableListOf()
        playerAWordsValidation = loadedPlayerAWordsValidation.toMutableList()
        playerBWordsValidation = loadedPlayerBWordsValidation.toMutableList()

        when (loadedTurn) {
            1 -> turn = Turn.FIRST
            2 -> turn = Turn.SECOND
            3 -> turn  = Turn.FINISHED
        }

        roundWinner = when (loadedWinner) {
            1 -> RoundWinner.PLAYERA
            2 -> RoundWinner.PLAYERB
            3 -> RoundWinner.DRAW
            else -> RoundWinner.NONE
        }
    }

    override fun instantiateTopicsAndLetter() {

    }

}