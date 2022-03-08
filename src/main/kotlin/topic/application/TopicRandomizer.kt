package com.example.topic.application

import com.example.topic.domain.ITopicLoader
import com.example.topic.domain.Topic
import com.example.topic.domain.ITopicRandomizer

class TopicRandomizer(topicLoaderDependency: ITopicLoader): ITopicRandomizer {
    private val topicLoader = topicLoaderDependency
    private lateinit var topics:List<Topic>

    override fun getRandomTopics(numberOfTopics: Int): List<String> {
        topics = topicLoader.LoadTopics()
        var randomTopicList: MutableList<String> = mutableListOf()
        val topicList = shuffleTopicsAndGet(numberOfTopics)

        topicList.forEach {
            randomTopicList.add(it.name)
        }

        return randomTopicList
    }

    private fun shuffleTopicsAndGet(numberOfTopics: Int) = topics.shuffled().take(numberOfTopics).toList()
}