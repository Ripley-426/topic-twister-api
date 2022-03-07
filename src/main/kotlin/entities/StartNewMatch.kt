package com.example.entities

import com.example.dao.MatchToSend
import com.example.dataSources.repositories.DBMatchLoader
import com.example.dataSources.repositories.interfaces.IMatchIDLoader
import com.example.dataSources.repositories.interfaces.IMatchLoader
import com.example.dataSources.repositories.interfaces.ITopicLoader
import com.example.dataSources.repositories.interfaces.IMatchDependencies
import com.example.dataSources.repositories.MatchDBDependencies
import com.example.entities.interfaces.ILetterRandomizer

class StartNewMatch {
    private val dependencies: IMatchDependencies = MatchDBDependencies()
    private val matchIDLoader: IMatchIDLoader = dependencies.matchIDLoader
    private val letterRandomizer: ILetterRandomizer = dependencies.letterRandomizer
    private val topicLoader: ITopicLoader = dependencies.topicLoader
    private val matchLoader: IMatchLoader = DBMatchLoader()

    fun createNewMatch(playerAID: Int): MatchToSend {

        val newMatch = Match(playerAID, matchIDLoader, letterRandomizer, topicLoader)
        matchLoader.saveMatch(newMatch)
        return MatchToSend(newMatch)
    }
}