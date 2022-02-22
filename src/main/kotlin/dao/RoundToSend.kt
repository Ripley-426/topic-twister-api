package com.example.dao

import com.example.enumClasses.RoundWinner
import com.example.enumClasses.Turn
import com.example.model.Round

data class RoundToSend(var roundNumber: Int = 0) {
    var letter: Char = 'A'
    var topics: List<String> = arrayListOf()
    var playerAWords: MutableList<String> = mutableListOf()
    var playerBWords: MutableList<String> = mutableListOf()
    var playerAWordsValidation = mutableListOf<Boolean>()
    var playerBWordsValidation = mutableListOf<Boolean>()

    var turn: Turn = Turn.FIRST
    var roundWinner: RoundWinner = RoundWinner.NONE

    fun convertRound(round: Round) {
        roundNumber = round.roundNumber
        letter = round.letter
        topics = round.topics
        playerAWords = round.playerAWords
        playerBWords = round.playerBWords
        playerAWordsValidation = round.playerAWordsValidation
        playerBWordsValidation = round.playerBWordsValidation
        turn = round.turn
        roundWinner = round.roundWinner
    }
}