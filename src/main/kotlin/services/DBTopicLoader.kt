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

    override fun LoadTopics(): MutableList<Topic> {
        var topics: MutableList<Topic> = mutableListOf()

        return topics
    }
}