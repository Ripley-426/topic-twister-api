package com.example.interfaces

interface ITopicRandomizer {
    fun getRandomTopics(numberOfTopics: Int): List<String>
}