package com.example.dataSources.repositories.interfaces

import com.example.entities.Topic

interface ITopicLoader {
    fun LoadTopics(): MutableList<Topic>
}