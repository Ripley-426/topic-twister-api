package com.example.model

import com.example.enumClasses.RoundWinner
import com.example.enumClasses.Turn
import com.example.services.TopicRandomizer
import com.example.services.WordValidator
import services.LetterRandomizer

class Round (val roundNumber: Int)
{
    var letter: Char
    var topics: List<String> = arrayListOf()
    private val topicRandomizer = TopicRandomizer()
    private val letterRandomizer = LetterRandomizer()
    private val wordsValidator = WordValidator()

    var playerAWords: MutableList<String> = mutableListOf()
    var playerBWords: MutableList<String> = mutableListOf()
    var playerAWordsValidation = mutableListOf<Boolean>()
    var playerBWordsValidation = mutableListOf<Boolean>()

    var turn: Turn = Turn.FIRST
    lateinit var roundWinner: RoundWinner

    init {
        topics = topicRandomizer.GetRandomTopics(5)
        letter = letterRandomizer.getRandomLetter()
    }

    fun readTopics(): List<String> {
        return topics
    }

    fun addWords(wordsList: MutableList<String>) {

        setWordsToPlayersAndValidate(wordsList)

        changeTurn()

        if (turn == Turn.FINISHED) { calculateRoundScore() }
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
        validatePlayerAWords()
    }

    private fun validatePlayerAWords() {
        playerAWordsValidation = wordsValidator.GetValidationResult(convertToValidationContainer(playerAWords))
    }

    private fun setWordsToPlayerBAndValidate(wordsList: MutableList<String>){
        playerBWords = wordsList
        validatePlayerBWords()
    }

    private fun validatePlayerBWords() {
        playerBWordsValidation = wordsValidator.GetValidationResult(convertToValidationContainer(playerBWords))
    }

    private fun convertToValidationContainer(wordList: MutableList<String>): ValidationContainer {
        var topicAndWordList: MutableList<TopicAndWord> = mutableListOf()
        topics.forEachIndexed { index, element ->
            topicAndWordList.add(TopicAndWord(element, wordList[index]))
        }

        return ValidationContainer(letter, topicAndWordList)
    }

    private fun calculateRoundScore(){
        roundWinner = if (playerAWordsValidation.count { it } > playerBWordsValidation.count { it }) {
            RoundWinner.PLAYERA
        } else if (playerAWordsValidation.count { it } < playerBWordsValidation.count { it }) {
            RoundWinner.PLAYERB
        } else {
            RoundWinner.DRAW
        }
    }

    private fun changeTurn() {
        when (turn) {
            Turn.FIRST -> turn = Turn.SECOND
            Turn.SECOND -> turn = Turn.FINISHED
        }
    }
}