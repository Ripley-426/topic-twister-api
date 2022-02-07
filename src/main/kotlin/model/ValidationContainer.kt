package com.example.model

data class ValidationContainer(
    val letter: String,
    val topicAndWord1: TopicAndWord,
    val topicAndWord2: TopicAndWord,
    val topicAndWord3: TopicAndWord,
    val topicAndWord4: TopicAndWord,
    val topicAndWord5: TopicAndWord,
)

data class TopicAndWord(
    val topic:String,
    val word:String
)
