package com.example.services

import com.example.interfaces.ITopicLoader
import com.example.model.Topic
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.net.URI

class DBTopicLoader: ITopicLoader {
    private val dataSource = dataSource()
    private val connection = dataSource.connection

    private fun initDb() {
        val stmt = connection.createStatement()
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TOPICS (topic text)")
    }

    private fun dataSource(): HikariDataSource {
        val config = HikariConfig()
        var dbUri = URI(System.getenv("DATABASE_URL") ?: "postgres://ssbjakmpycxnpo:3c868f613a973b52876ad1664be913f8ad23938d7d9b14870b2eeaecb26fe8cd@ec2-34-230-198-12.compute-1.amazonaws.com:5432/d3fq5745pjvp8i")
        var dbUserInfo =  dbUri.userInfo
        var username: String?; var password: String?;
        if (dbUserInfo != null) {
            username = dbUserInfo.split(":")[0]
            password = dbUserInfo.split(":")[1]
        } else {
            username = System.getenv("DATABASE_USERNAME") ?: "ssbjakmpycxnpo"
            password = System.getenv("DATABASE_PASSWORD") ?: "3c868f613a973b52876ad1664be913f8ad23938d7d9b14870b2eeaecb26fe8cd"
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

    override fun LoadTopics(): MutableList<Topic> {
        return GetAllTopicsFromDB()
    }

    fun GetAllTopicsFromDB():MutableList<Topic> {
        initDb()
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