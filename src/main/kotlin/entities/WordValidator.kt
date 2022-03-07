package com.example.entities

import com.example.dataSources.repositories.interfaces.ITopicLoader
import com.example.entities.interfaces.IWordValidator
import entities.ValidationContainer

class WordValidator(topicLoaderDependency: ITopicLoader) : IWordValidator {
    private val topicLoader = topicLoaderDependency
    private var topics:List<Topic> = mutableListOf()

    override fun getValidationResult(validationData: ValidationContainer) : MutableList<Boolean> {

        val result = mutableListOf<Boolean>()
        validationData.topicsAndWords.forEach() {
            result.add(validateAnswer(it, validationData.letter))
        }

        return result
    }

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

        return foundTopic.GetWords().contains(word)
    }
}