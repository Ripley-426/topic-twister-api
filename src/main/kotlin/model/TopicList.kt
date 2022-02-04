package com.example.model

class TopicList{

    private lateinit var topicList: MutableList<Topic>

    fun AddTopic(topic: Topic){
        topicList.add(topic)
    }

    fun GetTopics(): MutableList<Topic> {
        return topicList
    }

}