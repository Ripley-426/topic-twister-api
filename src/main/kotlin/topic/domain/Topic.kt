package com.example.topic.domain

class Topic(name: String, wordList: MutableList<String> = mutableListOf())
{
    val name = filterNonLettersOrSpacesAndConvertToUppercase(name)
    private var words: MutableList<String> = mutableListOf()

    init {
        if (wordList.isNotEmpty()) { addWords(wordList) }
    }

    fun addWord(newWord: String) {
        if (!words.contains(newWord) and (!newWord.isNullOrBlank())) {
            words.add(filterNonLettersOrSpacesAndConvertToUppercase(newWord))
        }
    }

    private fun addWords(newWords: List<String>) {
        newWords.forEach { addWord(it) }
    }

    fun getWords(): MutableList<String> {
        return words
    }

    private fun filterNonLettersOrSpacesAndConvertToUppercase(message:String): String {
        return message.uppercase().filter { it.isLetter() or (it == ' ')  }
    }
}