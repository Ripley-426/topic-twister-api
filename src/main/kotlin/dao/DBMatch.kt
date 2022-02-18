package com.example.dao

data class DBMatch(
    val id: Int,
    val playerAID:Int,
    val playerBID:Int? = null,
    val winner:Int? = null,
    val roundLetter:Char,
    val roundTopics:List<String>,
    val roundPlayerAWords:List<String>,
    val roundPlayerBWords:List<String>? = null,
    val roundPlayerAWordsValidation:List<Boolean>,
    val roundPlayerBWordsValidation:List<Boolean>,
    val roundTurn: Int,
    val roundWinner: Int? = null
) {

}