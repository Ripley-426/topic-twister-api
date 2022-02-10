package com.example.debugTools

import com.example.interfaces.ITopicLoader
import com.example.model.Topic
import com.example.tempPermanence.InMemoryTopicLoader
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.net.URI

class AddTopicsToDB {

    private val dataSource = dataSource()
    private val connection = dataSource.connection

    private fun initDb() {
        val stmt = connection.createStatement()
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TOPICS (topic text)")
    }

    private fun dataSource(): HikariDataSource {
        val config = HikariConfig()
        var dbUri = URI(System.getenv("DATABASE_URL") ?: "postgresql://localhost:5432/")
        var dbUserInfo =  dbUri.userInfo
        var username: String?; var password: String?;
        if (dbUserInfo != null) {
            username = dbUserInfo.split(":")[0]
            password = dbUserInfo.split(":")[1]
        } else {
            username = System.getenv("DATABASE_USERNAME") ?: null
            password = System.getenv("DATABASE_PASSWORD") ?: null
        }
        if (username != null) {
            config.username = username
        }
        if (password != null) {
            config.password = password
        }
        val dbUrl = "jdbc:postgresql://${dbUri.host}:${dbUri.port}${dbUri.path}"
        config.jdbcUrl = dbUrl
        return HikariDataSource(config)
    }

    fun addTopic(topicName:String, words:MutableList<String>) {
        initDb()
        val stmt = connection.createStatement()
        stmt.executeUpdate("INSERT INTO TOPICS values $topicName")
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS $topicName (words text)")

        val wordsString = words.joinToString()

        stmt.executeUpdate("INSERT INTO $topicName values $wordsString")
    }

    fun run() {
        val inMemoryTopicLoader = InMemoryTopicLoader()
        val topics = inMemoryTopicLoader.LoadTopics()

        topics.forEach { addTopic(it.name, it.GetWords()) }
    }
}