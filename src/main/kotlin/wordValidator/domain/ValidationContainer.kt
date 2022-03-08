package com.example.wordValidator.domain

import com.example.wordValidator.domain.TopicAndWord

data class ValidationContainer(
    val letter: Char,
    val topicsAndWords: List<TopicAndWord>
)
