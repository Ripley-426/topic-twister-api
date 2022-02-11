package com.example.model

import com.example.services.TopicRandomizer
import com.example.services.WordValidator

class Round (
    val letter: String
    )
{

    var topics: List<String> = arrayListOf()
    val topicRandomizer = TopicRandomizer()
    val wordsValidator = WordValidator()
    var wordsValidations = mutableListOf<Boolean>()
    var words: MutableList<String> = mutableListOf()

    init {
        topics = topicRandomizer.GetRandomTopics(5)
    }

    fun readTopics(): List<String> {
        return topics
    }

    fun addWords(wordsList: MutableList<String>) {
        words = wordsList
        validateWords()
    }

    fun validateWords() {

        wordsValidations = wordsValidator.GetValidationResult(convertToValidationContainer(words))
    }

    private fun convertToValidationContainer(wordList: MutableList<String>): ValidationContainer {
        var topicAndWordList: MutableList<TopicAndWord> = mutableListOf()
        topics.forEachIndexed { index, element ->
            topicAndWordList.add(TopicAndWord(element, wordList[index]))
        }

        return ValidationContainer(letter, topicAndWordList)
    }
}