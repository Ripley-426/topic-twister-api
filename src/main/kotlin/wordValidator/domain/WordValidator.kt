package com.example.wordValidator.domain

import com.example.topic.domain.ITopicLoader
import com.example.topic.domain.Topic

class WordValidator(topicLoaderDependency: ITopicLoader) {
    private val topicLoader = topicLoaderDependency
    private var topics:List<Topic> = mutableListOf()

    fun validateAnswer(topicAndWord: TopicAndWord, letter:Char): Boolean {
        if (topicAndWord.word == "") return false
        if (topicAndWord.word[0] != letter) return false
        return validate(topicAndWord.topic, topicAndWord.word)
    }

    fun validate(topicToValidate:String, wordToValidate:String): Boolean {

        if (topics.isEmpty()) {
            topics = topicLoader.LoadTopics()
        }

        val topic = topicToValidate.uppercase()
        val word = wordToValidate.uppercase()

        val topicExists = topics.any { it.name == topic }
        if (!topicExists) { return false }

        val foundTopic = topics.first() { it.name == topic}

        return foundTopic.getWords().contains(word)
    }
}