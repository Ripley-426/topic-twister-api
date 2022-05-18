package com.example.match.application

import com.example.match.dao.MatchToSend
import com.example.match.infrastructure.DBMatchLoader
import com.example.match.infrastructure.MatchDBDependencies
import com.example.letterRandomizer.ILetterRandomizer
import com.example.match.domain.*
import com.example.topic.application.TopicRandomizer
import com.example.wordValidator.application.ValidateWords

class StartNewMatch {
    private val dependencies: IMatchDependencies = MatchDBDependencies()
    private val matchLoader: IMatchLoader = DBMatchLoader()
    private val matchIDLoader: IMatchIDLoader = dependencies.matchIDLoader

    private val topicRandomizer = TopicRandomizer(dependencies.topicLoader)
    private val wordValidator = ValidateWords(dependencies.topicLoader)
    private val letterRandomizer: ILetterRandomizer = dependencies.letterRandomizer

    fun createNewMatch(playerAID: Int): MatchToSend {

        val roundsFactory = RoundFactory(topicRandomizer, letterRandomizer, wordValidator)
        val newMatch = Match(playerAID, roundsFactory, matchIDLoader)
        matchLoader.saveMatch(newMatch)
        val matchToSend = MatchToSend()
        matchToSend.convertMatch(matchLoader.loadMatch(newMatch.id))
        return matchToSend
    }
}