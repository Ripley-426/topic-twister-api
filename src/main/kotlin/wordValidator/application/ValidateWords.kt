package com.example.wordValidator.application

import com.example.topic.domain.ITopicLoader
import com.example.wordValidator.domain.IWordValidator
import com.example.wordValidator.domain.ValidationContainer
import com.example.wordValidator.domain.WordValidator

class ValidateWords(topicLoaderDependency: ITopicLoader):IWordValidator {

    private val wordValidator = WordValidator(topicLoaderDependency)

    override fun getValidationResult(validationData: ValidationContainer) : MutableList<Boolean> {

        val result = mutableListOf<Boolean>()
        validationData.topicsAndWords.forEach() {
            result.add(wordValidator.validateAnswer(it, validationData.letter))
        }

        return result
    }
}