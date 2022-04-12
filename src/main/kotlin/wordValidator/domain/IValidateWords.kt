package com.example.wordValidator.domain

interface IValidateWords {
    fun getValidationResult(letter: Char, topicList:List<String>, wordList: MutableList<String>) : MutableList<Boolean>
}