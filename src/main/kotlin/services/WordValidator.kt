package com.example.services

import com.example.model.Topic
import com.example.tempPermanence.LoadedTopics

class WordValidator {
    private val topicLoader = LoadedTopics()
    private val topics:List<Topic> = topicLoader.LoadTopics()

    fun validate(topic:String, word:String): Boolean{

        val topicExists = topics.any { it.name == topic }
        if (!topicExists) { return false }

        val foundTopic = topics.first() { it.name == topic}

        return foundTopic.GetWords().contains(word)
    }
}