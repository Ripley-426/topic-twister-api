package com.example.debugTools

import com.example.model.TopicAndWord
import com.example.model.ValidationContainer
import com.google.gson.Gson

class WordValidatorJSON {

    var gson = Gson()
    val topicAndWord1: TopicAndWord = TopicAndWord("ANIMALS", "A")
    val topicAndWord2: TopicAndWord = TopicAndWord("NAMES", "AA")
    val topicAndWord3: TopicAndWord = TopicAndWord("COUNTRIES", "B")
    val topicAndWord4: TopicAndWord = TopicAndWord("PLANTS", "AA")
    val topicAndWord5: TopicAndWord = TopicAndWord("JOBS", "A")

    val letter = "A"

    val validationContainer:ValidationContainer = ValidationContainer(
        letter, topicAndWord1, topicAndWord2, topicAndWord3, topicAndWord4, topicAndWord5)

    fun GetJsonExample(): String {
        var jsonString = gson.toJson(validationContainer)

        return jsonString
    }

}