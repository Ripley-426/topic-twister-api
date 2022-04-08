package entities

import com.example.match.domain.Match
import com.example.match.infrastructure.DBMatchIDLoader
import com.example.match.infrastructure.InMemoryTopicLoader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import com.example.letterRandomizer.LetterRandomizer
import com.example.match.domain.Round
import com.example.topic.application.TopicRandomizer
import com.example.wordValidator.application.ValidateWords

class MatchShould {

    private lateinit var match: Match
    private lateinit var listOfWordsThreeCorrect: MutableList<String>
    private lateinit var listOfWordsTwoCorrect: MutableList<String>
    private var playerAID: Int = 0
    private var playerBID: Int = 0


    @BeforeEach
    fun setUp(){

        val mockMatchIDLoaderDependency = Mockito.mock(DBMatchIDLoader::class.java)
        Mockito.`when`(mockMatchIDLoaderDependency.getID()).thenReturn(1)

        val mockLetterRandomizerDependency = Mockito.mock(LetterRandomizer::class.java)
        Mockito.`when`(mockLetterRandomizerDependency.getRandomLetter()).thenReturn('A')

        val topicLoaderDependency = InMemoryTopicLoader()

        val roundDependency = Round(0, TopicRandomizer(topicLoaderDependency), mockLetterRandomizerDependency, ValidateWords(topicLoaderDependency))

        playerAID = 1
        playerBID = 2
        match = Match(playerAID, roundDependency, mockMatchIDLoaderDependency)
        listOfWordsThreeCorrect = mutableListOf("A", "B", "A", "B", "A")
        listOfWordsTwoCorrect = mutableListOf("B", "B", "A", "B", "A")
    }

    @Test
    fun `have three rounds when created`() {
        val result = match.rounds.count()
        assertEquals(3, result)
    }

    @Test
    fun `not add second set of words without a playerB`() {
        match.addWords(listOfWordsThreeCorrect)
        match.addWords(listOfWordsTwoCorrect)
        val result = match.getCurrentRound().playerBWords.isEmpty()
        assertTrue(result)
    }

    @Test
    fun `change round when two set of words are added` () {
        match.addWords(listOfWordsThreeCorrect)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsTwoCorrect)

        val result = match.getCurrentRound().roundNumber

        assertEquals(2, result)
    }

    @Test
    fun `have player A as winner when the match is finished` () {

        match.addWords(listOfWordsThreeCorrect)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsTwoCorrect)

        match.addWords(listOfWordsTwoCorrect)
        match.addWords(listOfWordsThreeCorrect)

        match.addWords(listOfWordsThreeCorrect)
        match.addWords(listOfWordsTwoCorrect)

        val result = match.winner

        assertEquals(playerAID, result)
    }

    @Test
    fun `have player B as winner when the match is finished` () {

        match.addWords(listOfWordsTwoCorrect)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsThreeCorrect)

        match.addWords(listOfWordsThreeCorrect)
        match.addWords(listOfWordsTwoCorrect)

        match.addWords(listOfWordsTwoCorrect)
        match.addWords(listOfWordsThreeCorrect)

        val result = match.winner

        assertEquals(playerBID, result)
    }

    @Test
    fun `finish when two rounds are won` () {
        match.addWords(listOfWordsThreeCorrect)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsTwoCorrect)

        match.addWords(listOfWordsTwoCorrect)
        match.addWords(listOfWordsThreeCorrect)

        val result = match.winner
        assertEquals(playerAID, result)
    }




}