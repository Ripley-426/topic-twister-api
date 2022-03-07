package com.example.entities

class TopicList{

    private var topicList: MutableList<Topic> = mutableListOf()

    fun AddTopic(topic: Topic){
        topicList.add(topic)
    }

    fun GetTopics(): MutableList<Topic> {
        return topicList
    }

}