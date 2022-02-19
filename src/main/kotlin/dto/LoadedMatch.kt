package com.example.dto

import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.IMatchIDLoader
import com.example.interfaces.ITopicLoader
import com.example.model.Match
import com.example.model.Round

class LoadedMatch(
    playerAID: Int,
    matchIDLoaderDependency: IMatchIDLoader,
    letterRandomizerDependency: ILetterRandomizer,
    topicLoaderDependency: ITopicLoader,
    id: Int,
    playerBID:Int? = null,
    winner:Int? = null,
    round1Letter:Char,
    round1Topics:List<String>,
    round1PlayerAWords:List<String>,
    round1PlayerBWords:List<String>? = null,
    round1PlayerAWordsValidation:List<Boolean>,
    round1PlayerBWordsValidation:List<Boolean>,
    round1Turn: Int,
    round1Winner: Int? = null,
    round2Letter:Char? = null,
    round2Topics:List<String>? = null,
    round2PlayerAWords:List<String>? = null,
    round2PlayerBWords:List<String>? = null,
    round2PlayerAWordsValidation:List<Boolean>? = null,
    round2PlayerBWordsValidation:List<Boolean>? = null,
    round2Turn: Int? = null,
    round2Winner: Int? = null,
    round3Letter:Char? = null,
    round3Topics:List<String>? = null,
    round3PlayerAWords:List<String>? = null,
    round3PlayerBWords:List<String>? = null,
    round3PlayerAWordsValidation:List<Boolean>? = null,
    round3PlayerBWordsValidation:List<Boolean>? = null,
    round3Turn: Int? = null,
    round3Winner: Int? = null
) : Match(playerAID, matchIDLoaderDependency, letterRandomizerDependency, topicLoaderDependency) {
}