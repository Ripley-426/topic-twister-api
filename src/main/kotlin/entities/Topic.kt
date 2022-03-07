package com.example.entities

class Topic(name: String, wordList: MutableList<String> = mutableListOf())
{
    val name = FilterNonLettersOrSpacesAndConvertToUppercase(name)
    private var words: MutableList<String> = mutableListOf()

    init {
        if (wordList.isNotEmpty()) { AddWords(wordList) }
    }

    fun AddWord(newWord: String) {
        if (!words.contains(newWord) and (!newWord.isNullOrBlank())) {
            words.add(FilterNonLettersOrSpacesAndConvertToUppercase(newWord))
        }
    }

    fun AddWords(newWords: List<String>) {
        newWords.forEach { AddWord(it) }
    }

    fun RemoveWord(wordToRemove: String) {
        if (words.contains(wordToRemove)) {
            words.remove(wordToRemove)
        }
    }

    fun RemoveWords(wordsToRemove: List<String>) {
        wordsToRemove.forEach { RemoveWord(it) }
    }

    fun GetWords(): MutableList<String> {
        return words
    }

    private fun FilterNonLettersOrSpacesAndConvertToUppercase(message:String): String {
        return message.uppercase().filter { it.isLetter() or (it == ' ')  }
    }
}