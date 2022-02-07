package com.example.services

import com.example.model.Topic
import com.example.model.TopicAndWord
import com.example.model.ValidationContainer
import com.example.tempPermanence.LoadedTopics

class WordValidator {
    private val topicLoader = LoadedTopics()
    private val topics:List<Topic> = topicLoader.LoadTopics()

    fun GetValidationResult(validationData:ValidationContainer) : BooleanArray {

        val result = booleanArrayOf()
        result[0] = ValidateAnswer(validationData.topicAndWord1, validationData.letter)
        result[1] = ValidateAnswer(validationData.topicAndWord2, validationData.letter)
        result[2] = ValidateAnswer(validationData.topicAndWord3, validationData.letter)
        result[3] = ValidateAnswer(validationData.topicAndWord4, validationData.letter)
        result[4] = ValidateAnswer(validationData.topicAndWord5, validationData.letter)

        return result
    }

    fun ValidateAnswer(topicAndWord: TopicAndWord, letter:String): Boolean {
        if (topicAndWord.word[0].toString() != letter) return false
        return Validate(topicAndWord.topic, topicAndWord.word)
    }

    fun Validate(topic:String, word:String): Boolean {

        val topicExists = topics.any { it.name == topic }
        if (!topicExists) { return false }

        val foundTopic = topics.first() { it.name == topic}

        return foundTopic.GetWords().contains(word)
    }
}