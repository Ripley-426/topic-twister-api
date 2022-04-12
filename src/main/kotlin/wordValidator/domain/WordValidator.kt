package com.example.wordValidator.domain

import com.example.topic.domain.ITopicLoader
import com.example.topic.domain.Topic

class WordValidator(topicLoaderDependency: ITopicLoader) {
    private val topicLoader = topicLoaderDependency
    private var topics:List<Topic> = mutableListOf()

    fun validateAnswer(topicAndWord: TopicAndWord, letter:Char): Boolean {
        if (isWordEmpty(topicAndWord.word)) return false
        if (isWordFirstLetterDifferentThanRoundLetter(topicAndWord.word, letter)) return false
        return validate(topicAndWord.topic, topicAndWord.word)
    }

    fun validate(topicToValidate:String, wordToValidate:String): Boolean {

        if (topics.isEmpty()) {
            topics = getTopicsFromTopicLoader()
        }

        val topic = setStringToUppercase(topicToValidate)
        val word = setStringToUppercase(wordToValidate)

        if (topicExists(topic)) {
            return isWordContainedInTopic(topic, word)
        } else {
            return false
        }
    }

    private fun isWordFirstLetterDifferentThanRoundLetter(word: String, letter: Char) = word[0] != letter

    private fun isWordEmpty(word: String) = word == ""

    private fun getTopicsFromTopicLoader() = topicLoader.LoadTopics()

    private fun setStringToUppercase(string: String) = string.uppercase()

    private fun topicExists(topicName: String) = topics.any { it.name == topicName }

    private fun isWordContainedInTopic(topicName: String, word: String) =
        getTopic(topicName).getWords().contains(word)

    private fun getTopic(topicName: String) = topics.first() { it.name == topicName }
}