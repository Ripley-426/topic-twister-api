package com.example.topic.domain

import com.example.topic.domain.Topic

interface ITopicLoader {
    fun LoadTopics(): MutableList<Topic>
}