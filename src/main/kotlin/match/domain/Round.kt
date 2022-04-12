package com.example.match.domain

import com.example.match.domain.enumClasses.RoundWinner
import com.example.match.domain.enumClasses.Turn
import com.example.letterRandomizer.ILetterRandomizer
import com.example.topic.domain.ITopicRandomizer
import com.example.wordValidator.domain.IValidateWords

open class Round(
    val roundNumber: Int,
    val wordsValidator: IValidateWords

){
    var letter: Char = 'A'
    var topics: List<String> = arrayListOf()

    var playerAWords: MutableList<String> = mutableListOf()
    var playerBWords: MutableList<String> = mutableListOf()
    var playerAWordsValidation = mutableListOf<Boolean>()
    var playerBWordsValidation = mutableListOf<Boolean>()

    var turn: Turn = Turn.FIRST
    var roundWinner: RoundWinner = RoundWinner.NONE

    fun readTopics(): List<String> {
        return topics
    }

    fun addWordsAndChangeTurn(wordsList: MutableList<String>) {

        setWordsToPlayersAndValidate(wordsList)

        changeTurn()

        if (turn == Turn.FINISHED) { calculateRoundScore() }
    }

    fun getTurnInt():Int {
        return when (turn) {
            Turn.FIRST -> 1
            Turn.SECOND -> 2
            Turn.FINISHED -> 3
        }
    }

    fun getWinnerInt():Int {
        return when (roundWinner) {
            RoundWinner.PLAYERA -> 1
            RoundWinner.PLAYERB -> 2
            RoundWinner.DRAW -> 3
            else -> 0
        }
    }

    private fun setWordsToPlayersAndValidate(wordsList: MutableList<String>) {
        if (roundNumber == 2) {
            when (turn) {
                Turn.FIRST -> setWordsToPlayerBAndValidate(wordsList)
                Turn.SECOND -> setWordsToPlayerAAndValidate(wordsList)
            }
        } else {
            when (turn) {
                Turn.FIRST -> setWordsToPlayerAAndValidate(wordsList)
                Turn.SECOND -> setWordsToPlayerBAndValidate(wordsList)
            }
        }
    }

    private fun setWordsToPlayerAAndValidate(wordsList: MutableList<String>){
        playerAWords = wordsList
        playerAWordsValidation = validateWords(playerAWords)
    }

    private fun setWordsToPlayerBAndValidate(wordsList: MutableList<String>){
        playerBWords = wordsList
        playerBWordsValidation = validateWords(playerBWords)
    }

    private fun validateWords(wordsList: MutableList<String>) = wordsValidator.getValidationResult(letter, topics, wordsList)

    private fun calculateRoundScore(){
        roundWinner = if (playerAHasMoreCorrectWordsThanPlayerB()) {
            RoundWinner.PLAYERA
        } else if (playerAHasLessCorrectWordsThanPlayerB()) {
            RoundWinner.PLAYERB
        } else {
            RoundWinner.DRAW
        }
    }

    private fun playerAHasMoreCorrectWordsThanPlayerB() =
        playerAWordsValidation.count { it } > playerBWordsValidation.count { it }

    private fun playerAHasLessCorrectWordsThanPlayerB() =
        playerAWordsValidation.count { it } < playerBWordsValidation.count { it }

    private fun changeTurn() {
        when (turn) {
            Turn.FIRST -> turn = Turn.SECOND
            Turn.SECOND -> turn = Turn.FINISHED
        }
    }
}