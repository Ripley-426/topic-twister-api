package com.example.model

import com.example.enumClasses.RoundWinner
import com.example.enumClasses.Turn
import com.example.interfaces.*
import com.example.services.TopicRandomizer
import com.example.services.WordValidator

class Match (val playerAID: Int,
                  val matchIDLoaderDependency: IMatchIDLoader,
                  val letterRandomizerDependency: ILetterRandomizer,
                  val topicLoaderDependency: ITopicLoader
) {

    private val matchIDLoader = matchIDLoaderDependency
    private val letterRandomizer = letterRandomizerDependency
    private val topicRandomizer = TopicRandomizer(topicLoaderDependency)
    private val wordValidator = WordValidator(topicLoaderDependency)

    var id: Int = matchIDLoader.getID()
    var playerBID: Int? = null
    var winner: Int? = null
    var rounds: MutableList<Round> = mutableListOf()

    init {
        for (i in 1..3) {
            rounds.add(Round(i, topicRandomizer, letterRandomizer, wordValidator))
        }
    }

    fun addWords(words: MutableList<String>): Boolean {
        if (verifyCantAddWordsWithoutPlayerB()) { return false }
        getCurrentRound().addWords(words)

        verifyMatchIsFinished()
        return true
    }

    fun addPlayerB(playerID: Int) {
        playerBID = playerID
    }

    private fun verifyCantAddWordsWithoutPlayerB(): Boolean {
        return playerBID == null && getCurrentRound().roundNumber == 1 && getCurrentRound().turn == Turn.SECOND
    }

    private fun verifyMatchIsFinished() {
        if (rounds.last().turn == Turn.FINISHED) {
            calculateWinner()
        }
    }

    private fun calculateWinner() {
        winner = if  (rounds.count() { it.roundWinner == RoundWinner.PLAYERA } > 1 ) {
            playerAID
        } else if (rounds.count() { it.roundWinner == RoundWinner.PLAYERB } > 1 ) {
            playerBID
        } else {
            0
        }

    }

    fun getCurrentRound(): Round {
        return rounds.first() { it.turn != Turn.FINISHED }
    }
}