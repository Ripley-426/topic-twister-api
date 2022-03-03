package com.example.services

import com.example.dao.MatchToSend
import com.example.dependencies.MatchDBDependencies
import com.example.model.Match
import com.example.model.Player
import com.example.services.DBMatchIDLoader
import com.example.services.DBMatchLoader
import com.example.tempPermanence.InMemoryTopicLoader
import services.LetterRandomizer

class StartNewMatch {
    private val dependencies = MatchDBDependencies()
    private val matchIDLoaderDependency = dependencies.matchIDLoader
    private val letterRandomizerDependency = dependencies.letterRandomizer
    private val topicLoaderDependency = dependencies.topicLoader
    private val dbMatchLoader = DBMatchLoader()

    fun createNewMatch(playerAID: Int): MatchToSend {

        val newMatch = Match(playerAID, matchIDLoaderDependency, letterRandomizerDependency, topicLoaderDependency)
        dbMatchLoader.saveMatch(newMatch)
        val matchToSend = MatchToSend()
        matchToSend.convertMatch(dbMatchLoader.loadMatch(newMatch.id))
        return matchToSend
    }
}