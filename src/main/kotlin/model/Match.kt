package com.example.model

import com.example.enumClasses.RoundWinner
import com.example.enumClasses.Turn
import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.IMatchIDLoader
import com.example.interfaces.ITopicRandomizer
import com.example.interfaces.IWordValidator
import com.example.services.DBMatchIDLoader
import com.example.services.TopicRandomizer
import com.example.services.WordValidator
import services.LetterRandomizer

class Match (val playerA: Player) {

    private val matchIDLoader: IMatchIDLoader = DBMatchIDLoader()
    private val letterRandomizer:ILetterRandomizer = LetterRandomizer()
    private val topicRandomizer: ITopicRandomizer = TopicRandomizer()
    private val wordValidator:IWordValidator = WordValidator()

    var id: Int = matchIDLoader.getID()
    var playerB: Player? = null
    var winner: Int? = null
    var rounds: MutableList<Round> = mutableListOf()

    init {
        for (i in 1..3) {
            rounds.add(Round(i, topicRandomizer, letterRandomizer, wordValidator))
        }
    }

    fun addWords(words: MutableList<String>): Boolean {
        if (verifyCantAddWordsWithoutPlayerB()) { return  false }
        getCurrentRound().addWords(words)

        verifyMatchIsFinished()
        return true
    }

    fun addPlayerB(player: Player) {
        playerB = player
    }

    private fun verifyCantAddWordsWithoutPlayerB(): Boolean {
        return playerB == null && getCurrentRound().roundNumber == 1 && getCurrentRound().turn == Turn.SECOND
    }

    private fun verifyMatchIsFinished() {
        if (rounds.last().turn == Turn.FINISHED) {
            calculateWinner()
        }
    }

    private fun calculateWinner() {
        if  (rounds.count() { it.roundWinner == RoundWinner.PLAYERA } > 1 ) {
            winner = playerA.id
        } else if (rounds.count() { it.roundWinner == RoundWinner.PLAYERB } > 1 ) {
            winner = playerB?.id
        } else { winner = 0 }

    }

    fun getCurrentRound(): Round {
        return rounds.first() { it.turn != Turn.FINISHED }
    }
}