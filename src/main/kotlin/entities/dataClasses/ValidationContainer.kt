package com.example.entities.dataClasses

import com.example.entities.dataClasses.TopicAndWord

data class ValidationContainer(
    val letter: Char,
    val topicsAndWords: List<TopicAndWord>
)
