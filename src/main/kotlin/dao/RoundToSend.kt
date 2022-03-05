package com.example.dao

import com.example.model.Round

data class RoundToSend(val round: Round) {
    var roundNumber = round.roundNumber
    var letter = round.letter
    var topics = round.topics
    var playerAWords = round.playerAWords
    var playerBWords = round.playerBWords
    var playerAWordsValidation = round.playerAWordsValidation
    var playerBWordsValidation = round.playerBWordsValidation

    var turn = round.turn
    var roundWinner = round.roundWinner
}