package com.example.match.application

import com.example.match.dao.MatchToSend
import com.example.match.infrastructure.DBMatchLoader
import com.example.match.domain.IMatchIDLoader
import com.example.match.domain.IMatchLoader
import com.example.topic.domain.ITopicLoader
import com.example.match.domain.IMatchDependencies
import com.example.match.infrastructure.MatchDBDependencies
import com.example.match.domain.Match
import com.example.letterRandomizer.ILetterRandomizer

class StartNewMatch {
    private val dependencies: IMatchDependencies = MatchDBDependencies()
    private val matchIDLoader: IMatchIDLoader = dependencies.matchIDLoader
    private val letterRandomizer: ILetterRandomizer = dependencies.letterRandomizer
    private val topicLoader: ITopicLoader = dependencies.topicLoader
    private val matchLoader: IMatchLoader = DBMatchLoader()

    fun createNewMatch(playerAID: Int): MatchToSend {

        val newMatch = Match(playerAID, matchIDLoader, letterRandomizer, topicLoader)
        matchLoader.saveMatch(newMatch)
        val matchToSend = MatchToSend()
        matchToSend.convertMatch(matchLoader.loadMatch(newMatch.id))
        return matchToSend
    }
}