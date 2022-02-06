package com.example.model

class Topic(name: String)
{
    val name = name
    private var words: MutableList<String> = mutableListOf()

    fun AddWord(newWord: String) {
        words.add(newWord)
    }

    fun AddWords(newWords: List<String>){
        newWords.forEach { AddWord(it) }
    }

    fun RemoveWord(wordToRemove: String) {
        words.remove(wordToRemove)
    }

    fun GetWords(): MutableList<String> {
        return words
    }
}