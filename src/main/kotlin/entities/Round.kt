package com.example.entities

import entities.ValidationContainer
import com.example.entities.enumClasses.RoundWinner
import com.example.entities.enumClasses.Turn
import com.example.entities.interfaces.ILetterRandomizer
import com.example.entities.interfaces.ITopicRandomizer
import com.example.entities.interfaces.IWordValidator

open class Round (val roundNumber: Int,
                  topicRandomizerDependency: ITopicRandomizer,
                  letterRandomizerDependency: ILetterRandomizer,
                  wordValidatorDependency: IWordValidator
)
{
    var letter: Char = 'A'
    var topics: List<String> = arrayListOf()
    private val topicRandomizer = topicRandomizerDependency
    private val letterRandomizer = letterRandomizerDependency
    private val wordsValidator = wordValidatorDependency

    var playerAWords: MutableList<String> = mutableListOf()
    var playerBWords: MutableList<String> = mutableListOf()
    var playerAWordsValidation = mutableListOf<Boolean>()
    var playerBWordsValidation = mutableListOf<Boolean>()

    var turn: Turn = Turn.FIRST
    var roundWinner: RoundWinner = RoundWinner.NONE

    init {
        instantiateTopicsAndLetter()
    }

    open fun instantiateTopicsAndLetter() {
        topics = topicRandomizer.getRandomTopics(5)
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
        validatePlayerAWords()
    }

    private fun validatePlayerAWords() {
        playerAWordsValidation = wordsValidator.getValidationResult(convertToValidationContainer(playerAWords))
    }

    private fun setWordsToPlayerBAndValidate(wordsList: MutableList<String>){
        playerBWords = wordsList
        validatePlayerBWords()
    }

    private fun validatePlayerBWords() {
        playerBWordsValidation = wordsValidator.getValidationResult(convertToValidationContainer(playerBWords))
    }

    private fun convertToValidationContainer(wordList: MutableList<String>): ValidationContainer {
        var topicAndWordList: MutableList<TopicAndWord> = mutableListOf()
        topics.forEachIndexed { index, element ->
            if (index >= wordList.size) {
                topicAndWordList.add(TopicAndWord(element, ""))
            } else {
                topicAndWordList.add(TopicAndWord(element, wordList[index]))
            }
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

    fun debugSetLetter() {
        letter = 'A'
    }
}