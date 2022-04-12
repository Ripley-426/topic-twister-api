package com.example.wordValidator.application

import com.example.topic.domain.ITopicLoader
import com.example.wordValidator.domain.IValidateWords
import com.example.wordValidator.domain.TopicAndWord
import com.example.wordValidator.domain.ValidationContainer
import com.example.wordValidator.domain.WordValidator

class ValidateWords(topicLoaderDependency: ITopicLoader):IValidateWords {

    private val wordValidator = WordValidator(topicLoaderDependency)

    override fun getValidationResult(letter: Char, topicList: List<String>, wordList: MutableList<String>) : MutableList<Boolean> {

        val validationData = convertToValidationContainer(letter, topicList, wordList)
        val result = mutableListOf<Boolean>()
        validationData.topicsAndWords.forEach() {
            result.add(wordValidator.validateAnswer(it, validationData.letter))
        }

        return result
    }

    private fun convertToValidationContainer(letter: Char, topicList: List<String>, wordList: MutableList<String>): ValidationContainer {
        var topicAndWordList: MutableList<TopicAndWord> = mutableListOf()
        topicList.forEachIndexed { index, element ->
            if (index >= wordList.size) {
                topicAndWordList.add(TopicAndWord(element, ""))
            } else {
                topicAndWordList.add(TopicAndWord(element, wordList[index]))
            }
        }

        return ValidationContainer(letter, topicAndWordList)
    }
}