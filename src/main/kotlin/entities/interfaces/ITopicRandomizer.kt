package com.example.entities.interfaces

interface ITopicRandomizer {
    fun getRandomTopics(numberOfTopics: Int): List<String>
}