package com.example.match.dto

import com.example.match.infrastructure.MatchDBDependencies
import com.example.letterRandomizer.ILetterRandomizer
import com.example.match.domain.IMatchIDLoader
import com.example.topic.domain.ITopicLoader
import com.example.match.domain.Match
import com.example.topic.application.TopicRandomizer
import com.example.wordValidator.application.ValidateWords
import com.example.wordValidator.domain.WordValidator

class LoadedMatch(
    playerAID: Int,
    matchIDLoaderDependency: IMatchIDLoader,
    letterRandomizerDependency: ILetterRandomizer,
    topicLoaderDependency: ITopicLoader,
    loadedId: Int,
    loadedPlayerBID:Int? = null,
    loadedWinner:Int? = null,
    loadedRound1Letter:Char,
    loadedRound1Topics:List<String>,
    loadedRound1PlayerAWords:List<String>,
    loadedRound1PlayerBWords:List<String>,
    loadedRound1PlayerAWordsValidation:List<Boolean>,
    loadedRound1PlayerBWordsValidation:List<Boolean>,
    loadedRound1Turn: Int,
    loadedRound1Winner: Int,
    loadedRound2Letter:Char,
    loadedRound2Topics:List<String>,
    loadedRound2PlayerAWords:List<String>,
    loadedRound2PlayerBWords:List<String>,
    loadedRound2PlayerAWordsValidation:List<Boolean>,
    loadedRound2PlayerBWordsValidation:List<Boolean>,
    loadedRound2Turn: Int,
    loadedRound2Winner: Int,
    loadedRound3Letter:Char,
    loadedRound3Topics:List<String>,
    loadedRound3PlayerAWords:List<String>,
    loadedRound3PlayerBWords:List<String>,
    loadedRound3PlayerAWordsValidation:List<Boolean>,
    loadedRound3PlayerBWordsValidation:List<Boolean>,
    loadedRound3Turn: Int,
    loadedRound3Winner: Int
) : Match(playerAID, matchIDLoaderDependency, letterRandomizerDependency, topicLoaderDependency) {

    init {

        val dependencies = MatchDBDependencies()
        id = loadedId
        playerBID = loadedPlayerBID
        winner = loadedWinner

        rounds = mutableListOf()

        rounds.add(LoadedRound(1, TopicRandomizer(dependencies.topicLoader), dependencies.letterRandomizer, ValidateWords(dependencies.topicLoader), loadedRound1Letter,
        loadedRound1Topics, loadedRound1PlayerAWords, loadedRound1PlayerBWords, loadedRound1PlayerAWordsValidation, loadedRound1PlayerBWordsValidation, loadedRound1Turn, loadedRound1Winner))

        rounds.add(LoadedRound(2, TopicRandomizer(dependencies.topicLoader), dependencies.letterRandomizer, ValidateWords(dependencies.topicLoader), loadedRound2Letter,
            loadedRound2Topics, loadedRound2PlayerAWords, loadedRound2PlayerBWords, loadedRound2PlayerAWordsValidation, loadedRound2PlayerBWordsValidation, loadedRound2Turn,
            loadedRound2Winner))

        rounds.add(LoadedRound(3, TopicRandomizer(dependencies.topicLoader), dependencies.letterRandomizer, ValidateWords(dependencies.topicLoader), loadedRound3Letter,
            loadedRound3Topics, loadedRound3PlayerAWords, loadedRound3PlayerBWords, loadedRound3PlayerAWordsValidation, loadedRound3PlayerBWordsValidation, loadedRound3Turn,
            loadedRound3Winner))

    }

    override fun getMatchIDFromDB() {
    }

    override fun instantiateRounds() {

    }
}