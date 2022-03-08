package com.example.topic.domain

interface ITopicRandomizer {
    fun getRandomTopics(numberOfTopics: Int): List<String>
}