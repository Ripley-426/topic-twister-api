package com.example.model

import com.example.services.TopicRandomizer
import com.example.services.WordValidator

class Round (
    val letter: String
    )
{

    var turn: Turn = Turn.FIRST
    var topics: List<String> = arrayListOf()
    val topicRandomizer = TopicRandomizer()
    val wordsValidator = WordValidator()
    var wordsValidations = mutableListOf<Boolean>()
    var playerAWords: MutableList<String> = mutableListOf()
    var playerBWords: MutableList<String> = mutableListOf()

    init {
        topics = topicRandomizer.GetRandomTopics(5)
    }

    fun readTopics(): List<String> {
        return topics
    }

    fun addWords(wordsList: MutableList<String>) {
        playerAWords = wordsList

        if (turn == Turn.SECOND) {
            playerBWords = wordsList
        }


        validateWords()
        changeTurn()
    }

    private fun changeTurn() {
        turn = Turn.SECOND
    }

    fun validateWords() {

        wordsValidations = wordsValidator.GetValidationResult(convertToValidationContainer(playerAWords))
    }

    private fun convertToValidationContainer(wordList: MutableList<String>): ValidationContainer {
        var topicAndWordList: MutableList<TopicAndWord> = mutableListOf()
        topics.forEachIndexed { index, element ->
            topicAndWordList.add(TopicAndWord(element, wordList[index]))
        }

        return ValidationContainer(letter, topicAndWordList)
    }
}