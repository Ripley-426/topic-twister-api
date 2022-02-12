package com.example.debugTools

import com.example.DBConnection.HikariDBConnection
import com.example.interfaces.ITopicLoader
import com.example.model.Topic
import com.example.tempPermanence.InMemoryTopicLoader
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.net.URI

class AddTopicsToDB {

    private val db = HikariDBConnection
    private val connection = db.getConnection()


    fun addTopic(topicName:String, words:MutableList<String>) {
        val stmt = connection.createStatement()
        val upperTopicName = topicName.uppercase()
        stmt.executeUpdate("INSERT INTO TOPICS values ('$upperTopicName')")
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS $upperTopicName (words text)")

        var upperWords:MutableList<String> = mutableListOf()
        words.forEach { upperWords.add(it.uppercase()) }
        val wordsString = upperWords.joinToString()

        stmt.executeUpdate("INSERT INTO $upperTopicName values ('$wordsString')")
    }

    fun run() {
        val inMemoryTopicLoader = InMemoryTopicLoader()
        val topics = inMemoryTopicLoader.LoadTopics()

        topics.forEach { addTopic(it.name, it.GetWords()) }
    }
}