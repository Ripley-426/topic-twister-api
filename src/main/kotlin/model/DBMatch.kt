package com.example.model

class DBMatch(
    val id: Int,
    val playerAID:Int,
    val playerBID:Int? = null,
    val winner:Int? = null,
    val round1Letter:Char,
    val round2Letter:Char? = null,
    val round3Letter:Char? = null,
    val round1Topics:List<String>,
    val round2Topics:List<String>? = null,
    val round3Topics:List<String>? = null,
    val round1PlayerAWords:List<String>,
    val round2PlayerAWords:List<String>? = null,
    val round3PlayerAWords:List<String>? = null,
    val round1PlayerBWords:List<String>? = null,
    val round2PlayerBWords:List<String>? = null,
    val round3PlayerBWords:List<String>? = null,
    val round1PlayerAWordsValidation:List<Boolean>,
    val round2PlayerAWordsValidation:List<Boolean>? = null,
    val round3PlayerAWordsValidation:List<Boolean>? = null,
    val round1PlayerBWordsValidation:List<Boolean>,
    val round2PlayerBWordsValidation:List<Boolean>? = null,
    val round3PlayerBWordsValidation:List<Boolean>? = null,
    val round1Turn: Int,
    val round2Turn: Int? = null,
    val round3Turn: Int? = null,
    val round1Winner: Int? = null,
    val round2Winner: Int? = null,
    val round3Winner: Int? = null
) {

}