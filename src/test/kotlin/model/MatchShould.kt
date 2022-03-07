package model

import com.example.entities.enumClasses.Turn
import com.example.entities.Match
import com.example.dataSources.repositories.DBMatchIDLoader
import com.example.dataSources.repositories.InMemoryTopicLoader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import com.example.entities.useCases.LetterRandomizer

class MatchShould {

    private lateinit var match: Match
    private lateinit var listOfWordsPlayerA: MutableList<String>
    private lateinit var listOfWordsPlayerB: MutableList<String>
    private var playerAID: Int = 0
    private var playerBID: Int = 0


    @BeforeEach
    fun setUp(){

        val mockMatchIDLoaderDependency = Mockito.mock(DBMatchIDLoader::class.java)
        Mockito.`when`(mockMatchIDLoaderDependency.getID()).thenReturn(1)

        val mockLetterRandomizerDependency = Mockito.mock(LetterRandomizer::class.java)
        Mockito.`when`(mockLetterRandomizerDependency.getRandomLetter()).thenReturn('A')

        val topicLoaderDependency = InMemoryTopicLoader()

        playerAID = 1
        playerBID = 2
        match = Match(playerAID, mockMatchIDLoaderDependency, mockLetterRandomizerDependency, topicLoaderDependency)
        listOfWordsPlayerA = mutableListOf("A", "B", "A", "B", "A")
        listOfWordsPlayerB = mutableListOf("B", "B", "A", "B", "A")
    }

    @Test
    fun `have three rounds when created`() {

        val result = match.rounds.count()

        assertEquals(3, result)
    }

    @Test
    fun `advance the round turn after adding words`() {

        match.addWords(listOfWordsPlayerA)

        val result = match.getCurrentRound().turn

        assertTrue(result == Turn.SECOND)
    }

    @Test
    fun `not add second set of words without a playerB`() {
        match.addWords(listOfWordsPlayerA)
        match.addWords(listOfWordsPlayerB)

        val result = match.getCurrentRound().playerBWords.isEmpty()

        assertTrue(result)
    }

    @Test
    fun `change round when two set of words are added` () {

        match.addWords(listOfWordsPlayerA)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsPlayerB)

        val result = match.getCurrentRound().roundNumber

        assertEquals(2, result)
    }

    @Test
    fun `calculate a winner when the match is finished` () {

        match.addWords(listOfWordsPlayerA)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsPlayerB)

        match.addWords(listOfWordsPlayerB)
        match.addWords(listOfWordsPlayerA)

        match.addWords(listOfWordsPlayerA)
        match.addWords(listOfWordsPlayerB)

        val result = match.winner

        assertEquals(playerAID, result)
    }




}