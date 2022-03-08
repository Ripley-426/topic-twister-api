package com.example.entities

import com.example.dataSources.repositories.interfaces.IMatchIDLoader
import com.example.dataSources.repositories.interfaces.ITopicLoader
import com.example.entities.enumClasses.RoundWinner
import com.example.entities.enumClasses.Turn
import com.example.entities.interfaces.ILetterRandomizer
import com.example.entities.useCases.TopicRandomizer
import com.example.entities.useCases.WordValidator

open class Match constructor (val playerAID: Int,
                              matchIDLoaderDependency: IMatchIDLoader,
                              letterRandomizerDependency: ILetterRandomizer,
                              topicLoaderDependency: ITopicLoader
)
{
    private val matchIDLoader = matchIDLoaderDependency
    private val letterRandomizer = letterRandomizerDependency
    private val topicRandomizer = TopicRandomizer(topicLoaderDependency)
    private val wordValidator = WordValidator(topicLoaderDependency)

    var id: Int = 0
    var playerBID: Int? = null
    var winner: Int? = null
    var rounds: MutableList<Round> = mutableListOf()

    init {
        this.getMatchIDFromDB()
        this.instantiateRounds()
    }

    open fun instantiateRounds() {
        for (i in 1..3) {
            rounds.add(Round(i, topicRandomizer, letterRandomizer, wordValidator))
        }
    }

    open fun getMatchIDFromDB() {
        id = matchIDLoader.getID()
    }

    fun addWords(words: MutableList<String>) {
        if (isPlayerBMissingFromMatch()) { return }
        getCurrentRound().addWords(words)

        calculateWinnerIfMatchIsFinished()
    }

    fun addPlayerB(playerID: Int) {
        playerBID = playerID
    }

    private fun isPlayerBMissingFromMatch(): Boolean {
        return playerBID == null && getCurrentRound().roundNumber == 1 && getCurrentRound().turn == Turn.SECOND
    }

    private fun calculateWinnerIfMatchIsFinished() {
        if (isLastTurnFinished()) {
            calculateWinner()
        }
    }

    private fun isLastTurnFinished() = rounds.last().turn == Turn.FINISHED

    private fun calculateWinner() {
        val playerAWins = countNumberOfTurnsWonByPlayer(RoundWinner.PLAYERA)
        val playerBWins = countNumberOfTurnsWonByPlayer(RoundWinner.PLAYERB)

        winner = calculateWinner(playerAWins, playerBWins)

    }

    private fun calculateWinner(playerAWins: Int, playerBWins: Int):Int {
        return if (playerAWins == playerBWins) {
            0
        } else if (playerAWins > playerBWins) {
            playerAID
        } else {
            playerBID!!
        }
    }

    private fun countNumberOfTurnsWonByPlayer(enum: RoundWinner) = rounds.count { it.roundWinner == enum }

    fun getCurrentRound(): Round {
        return try {
            rounds.first { it.turn != Turn.FINISHED }
        } catch (e:NoSuchElementException) {
            rounds.last()
        }
    }

    fun currentTurnPlayerID(): Int? {
        val currentRound = getCurrentRound()
        return if (currentRound.roundNumber % 2 == 0) {
            if (currentRound.turn == Turn.FIRST) { playerBID } else { playerAID }
        } else {
            if (currentRound.turn == Turn.FIRST) { playerAID } else { playerBID }
        }
    }

}