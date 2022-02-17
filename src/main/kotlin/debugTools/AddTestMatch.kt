package com.example.debugTools

import com.example.model.Match
import com.example.model.Player
import com.example.services.DBMatchIDLoader
import com.example.services.DBMatchLoader
import com.example.tempPermanence.InMemoryTopicLoader
import services.LetterRandomizer

class AddTestMatch {
    private val playerA = Player(3,"Jose")
    private val listOfWordsPlayerA = mutableListOf("A", "B", "A", "B", "A")
    private val playerB = Player(6, "Martin")
    private val listOfWordsPlayerB = mutableListOf("B", "B", "A", "B", "A")
    private val matchIDLoaderDependency = DBMatchIDLoader()
    private val letterRandomizerDependency = LetterRandomizer()
    private val topicLoaderDependency = InMemoryTopicLoader()
    private val match: Match = Match(playerA.id, matchIDLoaderDependency, letterRandomizerDependency, topicLoaderDependency)
    private val dbMatchLoader = DBMatchLoader()

    fun saveTestMatch() {
        match.addWords(listOfWordsPlayerA)
        match.addPlayerB(playerB.id)
        match.addWords(listOfWordsPlayerB)

        match.addWords(listOfWordsPlayerB)
        match.addWords(listOfWordsPlayerA)

        match.addWords(listOfWordsPlayerA)
        match.addWords(listOfWordsPlayerB)

        dbMatchLoader.saveMatchToDB(match)

        val newMatch = Match(playerA.id, matchIDLoaderDependency, letterRandomizerDependency, topicLoaderDependency)
        dbMatchLoader.saveMatchToDB(newMatch)
    }
}