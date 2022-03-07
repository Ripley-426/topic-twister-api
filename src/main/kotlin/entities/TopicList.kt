package com.example.entities

class TopicList{

    private var topicList: MutableList<Topic> = mutableListOf()

    fun addTopic(topic: Topic){
        topicList.add(topic)
    }

    fun getTopics(): MutableList<Topic> {
        return topicList
    }

}