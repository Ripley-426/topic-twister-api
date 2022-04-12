package com.example.match.domain

import com.example.match.domain.enumClasses.RoundWinner
import com.example.match.domain.enumClasses.Turn

open class Match constructor (val playerAID: Int,
                              val roundsFactory: IRoundFactory,
                              val matchIDLoader: IMatchIDLoader
)
{
    var id: Int = 0
    var playerBID: Int? = null
    var winner: Int? = null
    var rounds: MutableList<Round> = mutableListOf()

    init {
        getMatchID()
        instantiateRounds()
    }

    private fun getMatchID() {
        id = matchIDLoader.getID()
    }

    open fun instantiateRounds() {
        rounds = roundsFactory.getRounds(3)
    }

    fun addWords(words: MutableList<String>) {
        if (isPlayerBMissingFromMatch()) { return }
        getCurrentRound().addWordsAndChangeTurn(words)

        calculateWinnerIfMatchIsFinished()
    }

    fun addWordsToFinishMatch(words: MutableList<String>){
        getCurrentRound().addWordsAndChangeTurn(words)
    }

    fun addPlayerB(playerID: Int) {
        playerBID = playerID
    }

    private fun isPlayerBMissingFromMatch(): Boolean {
        return playerBID == null && getCurrentRound().roundNumber == 1 && getCurrentRound().turn == Turn.SECOND
    }

    private fun calculateWinnerIfMatchIsFinished() {
        if (playerHasTwoWins()){
            completeMatch()
        }
        if (isLastTurnFinished()) {
            calculateWinner()
        }
    }

    private fun completeMatch() {
        val list : MutableList<String> = mutableListOf("A", "A", "A", "A", "A")
        addWordsToFinishMatch(list)
        addWordsToFinishMatch(list)
    }

    private fun playerHasTwoWins(): Boolean {
        val playerAWins = countNumberOfTurnsWonByPlayer(RoundWinner.PLAYERA)
        val playerBWins = countNumberOfTurnsWonByPlayer(RoundWinner.PLAYERB)
        if (playerAWins >= 2 || playerBWins >= 2){
            return true
        }
        return false
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
        return if (isCurrentRoundEven(currentRound)) {
            if (currentRound.turn == Turn.FIRST) { playerBID } else { playerAID }
        } else {
            if (currentRound.turn == Turn.FIRST) { playerAID } else { playerBID }
        }
    }

    private fun isCurrentRoundEven(currentRound: Round) = currentRound.roundNumber % 2 == 0

}