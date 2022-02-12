package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.interfaces.ITopicLoader
import com.example.model.Topic

class DBTopicLoader: ITopicLoader {
    private val db = HikariDBConnection
    private val connection = db.getConnection()

    override fun LoadTopics(): MutableList<Topic> {
        return GetAllTopicsFromDB()
    }

    fun GetAllTopicsFromDB():MutableList<Topic> {
        var topics:MutableList<Topic> = mutableListOf()
        val stmt = connection.createStatement()
        val topicListString = stmt.executeQuery("SELECT topic FROM TOPICS")

        while (topicListString.next()) {
            val topicName = topicListString.getString("topic")
            val wordsString = stmt.executeQuery("SELECT words from $topicName")
            wordsString.next()
            val words = wordsString.getString("words").replace(", ", ",").replace(" ,", ",")
            val wordsList:MutableList<String> = words.split(",").toMutableList()
            topics.add(Topic(topicListString.getString("topic"), wordsList))
        }

        return topics
    }
}