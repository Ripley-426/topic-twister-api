package com.example.services

import com.example.interfaces.ITopicLoader
import com.example.model.Topic
import com.example.model.TopicAndWord
import com.example.model.ValidationContainer
import com.example.tempPermanence.InMemoryTopicLoader

class WordValidator {
    private val topicLoader:ITopicLoader = InMemoryTopicLoader()
    private val topics:List<Topic> = topicLoader.LoadTopics()

    fun GetValidationResult(validationData:ValidationContainer) : MutableList<Boolean> {

        val result = mutableListOf<Boolean>()
        validationData.topicsAndWords.forEach() {
            result.add(ValidateAnswer(it, validationData.letter))
        }

        return result
    }

    fun ValidateAnswer(topicAndWord: TopicAndWord, letter:String): Boolean {
        if (topicAndWord.word[0].toString() != letter) return false
        return Validate(topicAndWord.topic, topicAndWord.word)
    }

    fun Validate(topic:String, word:String): Boolean {

        val topic = topic.uppercase()
        val word = word.uppercase()

        val topicExists = topics.any { it.name == topic }
        if (!topicExists) { return false }

        val foundTopic = topics.first() { it.name == topic}

        return foundTopic.GetWords().contains(word)
    }
}