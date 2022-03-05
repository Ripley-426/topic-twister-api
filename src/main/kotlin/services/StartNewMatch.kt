package com.example.services

import com.example.dao.MatchToSend
import com.example.dependencies.MatchDBDependencies
import com.example.model.Match

class StartNewMatch {
    private val dependencies = MatchDBDependencies()
    private val matchIDLoaderDependency = dependencies.matchIDLoader
    private val letterRandomizerDependency = dependencies.letterRandomizer
    private val topicLoaderDependency = dependencies.topicLoader
    private val dbMatchLoader = DBMatchLoader()

    fun createNewMatch(playerAID: Int): MatchToSend {

        val newMatch = Match(playerAID, matchIDLoaderDependency, letterRandomizerDependency, topicLoaderDependency)
        dbMatchLoader.saveMatch(newMatch)
        return MatchToSend(newMatch)
    }
}