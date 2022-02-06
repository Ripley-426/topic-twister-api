package com.example.interfaces

import com.example.model.Topic

interface ITopicLoader {
    fun LoadTopics(): MutableList<Topic>
}