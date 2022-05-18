package entities

import com.example.match.domain.Match
import com.example.match.infrastructure.DBMatchIDLoader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import com.example.letterRandomizer.LetterRandomizer
import com.example.match.domain.RoundFactory
import com.example.match.infrastructure.MatchTestDependencies
import com.example.topic.application.TopicRandomizer
import com.example.wordValidator.application.ValidateWords

class MatchShould {

    private val dependencies = MatchTestDependencies()
    private lateinit var match: Match
    private lateinit var listOfWordsThreeCorrect: MutableList<String>
    private lateinit var listOfWordsTwoCorrect: MutableList<String>
    private var playerAID: Int = 0
    private var playerBID: Int = 0


    @BeforeEach
    fun setUp(){

        val mockMatchIDLoaderDependency = createTestDoubleMatchIDLoader()
        val mockLetterRandomizerDependency = createTestDoubleLetterRandomizer()
        val topicRandomizerDependency = createTestDoubleTopicRandomizer()
        val validateWordsDependency = createTestDoubleValidateWords()

        listOfWordsThreeCorrect = mutableListOf("AnimalsWord", "", "NamesWord", "", "PlantsWord")
        listOfWordsTwoCorrect = mutableListOf(" ", " ", "NamesWord", "CountriesWord", "")

        addTestLogicToTestDoubles(mockMatchIDLoaderDependency,
            mockLetterRandomizerDependency,
            topicRandomizerDependency,
            validateWordsDependency)

        val roundsFactory = RoundFactory(topicRandomizerDependency, mockLetterRandomizerDependency, validateWordsDependency)

        playerAID = 1
        playerBID = 2
        match = Match(playerAID, roundsFactory, mockMatchIDLoaderDependency)
    }


    //region SetupFunctions
    private fun addTestLogicToTestDoubles(
        mockMatchIDLoaderDependency: DBMatchIDLoader,
        mockLetterRandomizerDependency: LetterRandomizer,
        topicRandomizerDependency: TopicRandomizer,
        validateWordsDependency: ValidateWords,
    ) {
        addReturnFixedMatchID(mockMatchIDLoaderDependency)
        addReturnFixedLetter(mockLetterRandomizerDependency)
        addReturnFixedTopicList(topicRandomizerDependency)
        addReturnValidationForThreeCorrectWords(validateWordsDependency, topicRandomizerDependency)
        addReturnValidationForTwoCorrectWords(validateWordsDependency, topicRandomizerDependency)
    }

    private fun addReturnFixedLetter(mockLetterRandomizerDependency: LetterRandomizer) {
        Mockito.`when`(mockLetterRandomizerDependency.getRandomLetter()).thenReturn('A')
    }

    private fun addReturnFixedMatchID(mockMatchIDLoaderDependency: DBMatchIDLoader) {
        Mockito.`when`(mockMatchIDLoaderDependency.getID()).thenReturn(1)
    }

    private fun createTestDoubleMatchIDLoader() = Mockito.mock(DBMatchIDLoader::class.java)

    private fun createTestDoubleLetterRandomizer() = Mockito.mock(LetterRandomizer::class.java)

    private fun addReturnFixedTopicList(topicRandomizerDependency: TopicRandomizer) {
        Mockito.`when`(topicRandomizerDependency.getRandomTopics(5)).thenReturn(
            mutableListOf("ANIMALS", "JOBS", "NAMES", "COUNTRIES", "PLANTS")
        )
    }

    private fun createTestDoubleTopicRandomizer() = Mockito.mock(TopicRandomizer::class.java)

    private fun addReturnValidationForTwoCorrectWords(
        validateWordsDependency: ValidateWords,
        topicRandomizerDependency: TopicRandomizer,
    ) {
        Mockito.`when`(validateWordsDependency.getValidationResult('A',
            topicRandomizerDependency.getRandomTopics(5),
            listOfWordsTwoCorrect
        )).thenReturn(mutableListOf(false, false, true, true, false))
    }

    private fun addReturnValidationForThreeCorrectWords(
        validateWordsDependency: ValidateWords,
        topicRandomizerDependency: TopicRandomizer,
    ) {
        Mockito.`when`(validateWordsDependency.getValidationResult('A',
            topicRandomizerDependency.getRandomTopics(5),
            listOfWordsThreeCorrect
        )).thenReturn(mutableListOf(true, false, true, false, true))
    }

    private fun createTestDoubleValidateWords() = Mockito.mock(ValidateWords::class.java)
    //endregion

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
        addSecondPlayerAndFirstPlayerWinsTheRound()

        val result = match.getCurrentRound().roundNumber

        assertEquals(2, result)
    }

    @Test
    fun `have player A as winner when the match is finished` () {
        addSecondPlayerAndFirstPlayerWinsTheRound()
        secondPlayerWinTheRound()
        firstPlayerWinTheRound()

        val result = match.winner

        assertEquals(playerAID, result)
    }

    @Test
    fun `have player B as winner when the match is finished` () {
        addSecondPlayerAndSecondPlayerWinsTheRound()
        firstPlayerWinTheRound()
        secondPlayerWinTheRound()

        val result = match.winner

        assertEquals(playerBID, result)
    }

    @Test
    fun `finish when two rounds are won by player A` () {
        addSecondPlayerAndFirstPlayerWinsTheRound()
        secondPlayerWinTheRound()

        val result = match.winner

        assertEquals(playerAID, result)
    }

    @Test
    fun `finish when two rounds are won by player B` () {
        addSecondPlayerAndSecondPlayerWinsTheRound()
        firstPlayerWinTheRound()

        val result = match.winner

        assertEquals(playerBID, result)
    }

    private fun secondPlayerWinTheRound() {
        match.addWords(listOfWordsTwoCorrect)
        match.addWords(listOfWordsThreeCorrect)
    }

    private fun addSecondPlayerAndFirstPlayerWinsTheRound() {
        match.addWords(listOfWordsThreeCorrect)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsTwoCorrect)
    }


    private fun addSecondPlayerAndSecondPlayerWinsTheRound() {
        match.addWords(listOfWordsTwoCorrect)
        match.addPlayerB(playerBID)
        match.addWords(listOfWordsThreeCorrect)
    }

    private fun firstPlayerWinTheRound() {
        match.addWords(listOfWordsThreeCorrect)
        match.addWords(listOfWordsTwoCorrect)
    }
}