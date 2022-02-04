package com.example.model

class Topic(val name: String)
{
    private lateinit var words: MutableList<String>

    fun AddWords(newWord: String) {
        words.add(newWord)
    }

    fun RemoveWord(wordToRemove: String) {
        words.remove(wordToRemove)
    }
}