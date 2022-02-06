package com.example.model

class Topic(name: String)
{
    val name = name
    private lateinit var words: MutableList<String>

    fun AddWords(newWord: String) {
        words.add(newWord)
    }

    fun RemoveWord(wordToRemove: String) {
        words.remove(wordToRemove)
    }

    fun GetWords(): MutableList<String> {
        return words
    }
}