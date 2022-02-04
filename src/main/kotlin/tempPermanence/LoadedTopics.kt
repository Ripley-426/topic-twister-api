package com.example.tempPermanence

import com.example.model.Topic
import com.example.model.TopicList

class LoadedTopics {
    private var topicList:TopicList = TopicList()

    init {
        AddTopics()
    }

    fun AddTopics() {
        var animalTopic: Topic = Topic("ANIMALS")
        topicList.AddTopic(animalTopic)
        var namesTopic: Topic = Topic("NAMES")
        topicList.AddTopic(namesTopic)
        var countriesTopic: Topic = Topic("COUNTRIES")
        topicList.AddTopic(countriesTopic)
        var plantsTopic: Topic = Topic("PLANTS")
        topicList.AddTopic(plantsTopic)
        var jobsTopic: Topic = Topic("JOBS")
        topicList.AddTopic(jobsTopic)
    }

    fun AddAlphabetToTopicList(){
        for (topic in topicList.GetTopics()){
            AddAlphabet(topic)
        }
    }

    fun AddAlphabet(topic: Topic) {
        for (letter in ('A'..'Z')) {
            topic.AddWords(letter.toString())
        }
    }

    fun GetTopics():MutableList<Topic>{
        return topicList.GetTopics()
    }
}