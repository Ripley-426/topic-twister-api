package com.example.model

data class ValidationContainer(
    val letter: String,
    val topicsAndWords: List<TopicAndWord>
)

data class TopicAndWord(
    val topic:String,
    val word:String
)
