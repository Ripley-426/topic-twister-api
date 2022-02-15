package com.example.services

import com.example.interfaces.ITopicLoader
import com.example.interfaces.ITopicRandomizer
import com.example.model.Topic
import com.example.tempPermanence.InMemoryTopicLoader

class TopicRandomizer: ITopicRandomizer {
    //private val topicLoader:ITopicLoader = DBTopicLoader()
    private val topicLoader:ITopicLoader = InMemoryTopicLoader()
    private val topics:List<Topic> = topicLoader.LoadTopics()

    override fun getRandomTopics(numberOfTopics: Int): List<String> {
        var randomTopicList: MutableList<String> = mutableListOf()
        val topicList = ShuffleTopicsAndGet(numberOfTopics)

        topicList.forEach {
            randomTopicList.add(it.name)
        }

        return randomTopicList
    }

    private fun ShuffleTopicsAndGet(numberOfTopics: Int) = topics.shuffled().take(numberOfTopics).toList()
}