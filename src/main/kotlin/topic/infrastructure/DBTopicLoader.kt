package com.example.topic.infrastructure

import com.example.match.infrastructure.dataSources.HikariDBConnection
import com.example.topic.domain.ITopicLoader
import com.example.topic.domain.Topic

class DBTopicLoader: ITopicLoader {
    private val db = HikariDBConnection
    private val stmt = db.getStatement()

    private var topics: MutableList<Topic> = mutableListOf()

    override fun LoadTopics(): MutableList<Topic> {
        if (topics.isEmpty()) { getAllTopicsFromDB()}
        return topics
    }

    private fun getAllTopicsFromDB() {
        val topicListString = stmt.executeQuery("SELECT topic FROM TOPICS")

        while (topicListString.next()) {
            val topicName = topicListString.getString("topic")
            val wordsString = stmt.executeQuery("SELECT words from $topicName")
            wordsString.next()
            val words = wordsString.getString("words").replace(", ", ",").replace(" ,", ",")
            val wordsList:MutableList<String> = words.split(",").toMutableList()
            topics.add(Topic(topicListString.getString("topic"), wordsList))
        }
    }
}