package com.example.debugTools

import com.example.model.TopicAndWord
import com.example.model.ValidationContainer
import com.google.gson.Gson

class WordValidatorJSON {

    var gson = Gson()
    var topicsAndWords = AddTopicsAndWords()
    val letter = "A"
    val validationContainer:ValidationContainer = ValidationContainer(letter, topicsAndWords)

    fun GetJsonExample(): String {
        var jsonString = gson.toJson(validationContainer)

        return jsonString
    }

    fun AddTopicsAndWords(): List<TopicAndWord> {

        val topicAndWord1 = TopicAndWord("ANIMALS", "A")
        val topicAndWord2 = TopicAndWord("NAMES", "AA")
        val topicAndWord3 = TopicAndWord("COUNTRIES", "B")
        val topicAndWord4 = TopicAndWord("PLANTS", "AA")
        val topicAndWord5 = TopicAndWord("JOBS", "A")

        var topicsAndWordsList = mutableListOf<TopicAndWord>()
        topicsAndWordsList.add(topicAndWord1)
        topicsAndWordsList.add(topicAndWord2)
        topicsAndWordsList.add(topicAndWord3)
        topicsAndWordsList.add(topicAndWord4)
        topicsAndWordsList.add(topicAndWord5)

        return topicsAndWordsList
    }

}