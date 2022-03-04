package com.example.services

import com.example.interfaces.ITopicLoader
import com.example.interfaces.IWordValidator
import com.example.model.Topic
import com.example.model.TopicAndWord
import com.example.model.ValidationContainer
import com.example.tempPermanence.InMemoryTopicLoader

class WordValidator(topicLoaderDependency: ITopicLoader) : IWordValidator{
    private val topicLoader = topicLoaderDependency
    private var topics:List<Topic> = mutableListOf()

    override fun getValidationResult(validationData:ValidationContainer) : MutableList<Boolean> {

        val result = mutableListOf<Boolean>()
        validationData.topicsAndWords.forEach() {
            result.add(ValidateAnswer(it, validationData.letter))
        }

        return result
    }

    fun ValidateAnswer(topicAndWord: TopicAndWord, letter:Char): Boolean {
        if (topicAndWord.word == "") return false
        if (topicAndWord.word[0] != letter) return false
        return Validate(topicAndWord.topic, topicAndWord.word)
    }

    fun Validate(topicToValidate:String, wordToValidate:String): Boolean {

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