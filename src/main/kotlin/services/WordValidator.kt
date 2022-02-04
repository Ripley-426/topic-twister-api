package com.example.services

import com.example.model.Topic
import com.example.tempPermanence.LoadedTopics

class WordValidator {
    private val topicLoader = LoadedTopics()
    private val topics:List<Topic> = topicLoader.GetTopics()

    
}