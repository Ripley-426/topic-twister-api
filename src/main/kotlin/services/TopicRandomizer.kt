package com.example.services

import com.example.model.Topic
import com.example.tempPermanence.LoadedTopics

class TopicRandomizer {
    private val topicLoader = LoadedTopics()
    private val topics:List<Topic> = topicLoader.LoadTopics()

    fun GetRandomTopics(numberOfTopics: Int): List<String> {
        var randomTopicList: MutableList<String> = mutableListOf()
        val topicList = topics.shuffled().take(numberOfTopics).toList()
        topicList.forEach {
            randomTopicList.add(it.name)
        }

        return randomTopicList
    }
}