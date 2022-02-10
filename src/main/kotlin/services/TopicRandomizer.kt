package com.example.services

import com.example.model.Topic
import com.example.tempPermanence.InMemoryTopicLoader

class TopicRandomizer {
    private val topicLoader = InMemoryTopicLoader()
    private val topics:List<Topic> = topicLoader.LoadTopics()

    fun GetRandomTopics(numberOfTopics: Int): List<String> {
        var randomTopicList: MutableList<String> = mutableListOf()
        val topicList = ShuffleTopicsAndGet(numberOfTopics)

        topicList.forEach {
            randomTopicList.add(it.name)
        }

        return randomTopicList
    }

    private fun ShuffleTopicsAndGet(numberOfTopics: Int) = topics.shuffled().take(numberOfTopics).toList()
}