package entities

import com.example.match.domain.Round
import com.example.match.domain.enumClasses.RoundWinner
import com.example.match.domain.enumClasses.Turn
import com.example.topic.application.TopicRandomizer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import com.example.letterRandomizer.LetterRandomizer
import com.example.match.domain.RoundFactory
import com.example.wordValidator.application.ValidateWords

class RoundShould {
    private lateinit var firstRound: Round
    private lateinit var secondRound: Round
    private lateinit var listOfWordsThreeCorrect: MutableList<String>
    private lateinit var listOfWordsTwoCorrect: MutableList<String>

    @BeforeEach
    fun setup(){

        val mockLetterRandomizerDependency = createTestDoubleLetterRandomizer()
        val topicRandomizerDependency = createTestDoubleTopicRandomizer()
        val validateWordsDependency = createTestDoubleValidateWords()
        listOfWordsThreeCorrect = mutableListOf("AnimalsWord", "", "NamesWord", "", "PlantsWord")
        listOfWordsTwoCorrect = mutableListOf(" ", " ", "NamesWord", "CountriesWord", "")

        addTestLogicToTestDoubles(mockLetterRandomizerDependency,topicRandomizerDependency,validateWordsDependency)

        val rounds = RoundFactory(topicRandomizerDependency, mockLetterRandomizerDependency, validateWordsDependency).getRounds(3)
        firstRound = rounds[0]
        secondRound = rounds[1]
    }

    //region Setup Functions

    private fun createTestDoubleLetterRandomizer() = Mockito.mock(LetterRandomizer::class.java)
    private fun createTestDoubleTopicRandomizer() = Mockito.mock(TopicRandomizer::class.java)
    private fun createTestDoubleValidateWords() = Mockito.mock(ValidateWords::class.java)

    private fun addTestLogicToTestDoubles(mockLetterRandomizerDependency: LetterRandomizer,
        topicRandomizerDependency: TopicRandomizer,
        validateWordsDependency: ValidateWords,
    ) {
        addReturnFixedLetter(mockLetterRandomizerDependency)
        addReturnFixedTopicList(topicRandomizerDependency)
        addReturnValidationForTwoCorrectWords(validateWordsDependency, topicRandomizerDependency)
        addReturnValidationForThreeCorrectWords(validateWordsDependency, topicRandomizerDependency)
    }

    private fun addReturnFixedLetter(mockLetterRandomizerDependency: LetterRandomizer) {
        Mockito.`when`(mockLetterRandomizerDependency.getRandomLetter()).thenReturn('A')
    }

    private fun addReturnFixedTopicList(topicRandomizerDependency: TopicRandomizer) {
        Mockito.`when`(topicRandomizerDependency.getRandomTopics(5)).thenReturn(
            mutableListOf("ANIMALS", "JOBS", "NAMES", "COUNTRIES", "PLANTS"))
    }

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
    //endregion

    //region Basic Tests

    @Test
    fun `have a letter`() {
        val result = getFirstRoundLetter()

        assertTrue(result.isLetter())
    }

    @Test
    fun `have five Topics`() {
        val result = countFirstRoundTopics()

        assertEquals(5, result)
    }

    @Test
    fun `Change to second turn after adding first words`() {
        addThreeCorrectWordsToFirstRound()
        val result = getFirstRoundTurn()

        assertEquals(Turn.SECOND, result)
    }

    @Test
    fun `change to turn to finished after adding second set of words`() {
        addThreeCorrectWordsToFirstRound()
        addTwoCorrectWordsToFirstRound()

        val result = getFirstRoundTurn()

        assertEquals(Turn.FINISHED, result)
    }

    @Test
    fun `calculate score when the round is over`() {
        addThreeCorrectWordsToFirstRound()
        addTwoCorrectWordsToFirstRound()

        val result = getFirstRoundWinner()

        assertEquals(RoundWinner.PLAYERA, result)
    }
    //endregion

    //region First Round Tests
    @Test
    fun `add first set of words to Player A on first turn`() {
        addThreeCorrectWordsToFirstRound()
        val result = getFirstRoundPlayerAWords()

        assertEquals(listOfWordsThreeCorrect, result)
    }

    @Test
    fun `validate playerA words after adding them on first turn`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(true, false, true, false, true)

        addThreeCorrectWordsToFirstRound()
        val result = getFirstRoundPlayerAValidations()

        assertEquals(expectedBoolList, result)
    }

    @Test
    fun `add second set of words to Player B on first round`() {
        addThreeCorrectWordsToFirstRound()
        addTwoCorrectWordsToFirstRound()
        val result = getFirstRoundPlayerBWords()

        assertEquals(listOfWordsTwoCorrect, result)
    }

    @Test
    fun `validate playerB words after adding them on first round`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(false, false, true, false, true)

        addThreeCorrectWordsToFirstRound()
        addTwoCorrectWordsToFirstRound()
        val result = getFirstRoundPlayerBValidations()

        assertEquals(expectedBoolList, result)
    }
    //endregion

    //region Second Round Tests
    @Test
    fun `add first set of words to Player B on second turn`() {
        addTwoCorrectWordsToSecondRound()
        val result = getSecondRoundPlayerBWords()

        assertEquals(listOfWordsTwoCorrect, result)
    }

    @Test
    fun `validate playerB words after adding them on second round`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(false, false, true, false, true)

        addTwoCorrectWordsToSecondRound()
        val result = getSecondRoundPlayerBValidations()

        assertEquals(expectedBoolList, result)
    }

    @Test
    fun `add second set of words to Player A on second turn`() {
        addTwoCorrectWordsToSecondRound()
        addThreeCorrectWordsToSecondRound()
        val result = getSecondRoundPlayerAWords()

        assertEquals(listOfWordsThreeCorrect, result)
    }

    @Test
    fun `validate playerA words after adding them on second turn`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(true, false, true, false, true)

        addTwoCorrectWordsToSecondRound()
        addThreeCorrectWordsToSecondRound()
        val result = getSecondRoundPlayerAValidations()

        assertEquals(expectedBoolList, result)
    }
    //endregion

    //region Test Functions

    private fun getFirstRoundLetter() = firstRound.letter
    private fun countFirstRoundTopics() = firstRound.readTopics().count()
    private fun getFirstRoundTurn() = firstRound.turn
    private fun getFirstRoundWinner() = firstRound.roundWinner
    private fun getFirstRoundPlayerAWords() = firstRound.playerAWords
    private fun getFirstRoundPlayerAValidations() = firstRound.playerAWordsValidation
    private fun getFirstRoundPlayerBWords() = firstRound.playerBWords
    private fun getFirstRoundPlayerBValidations() = firstRound.playerBWordsValidation
    private fun getSecondRoundPlayerAWords() = secondRound.playerAWords
    private fun getSecondRoundPlayerAValidations() = secondRound.playerAWordsValidation
    private fun getSecondRoundPlayerBWords() = secondRound.playerBWords
    private fun getSecondRoundPlayerBValidations() = secondRound.playerBWordsValidation

    private fun addThreeCorrectWordsToFirstRound() {
        firstRound.addWordsAndChangeTurn(listOfWordsThreeCorrect)
    }
    private fun addTwoCorrectWordsToFirstRound() {
        firstRound.addWordsAndChangeTurn(listOfWordsTwoCorrect)
    }
    private fun addThreeCorrectWordsToSecondRound() {
        secondRound.addWordsAndChangeTurn(listOfWordsThreeCorrect)
    }
    private fun addTwoCorrectWordsToSecondRound() {
        secondRound.addWordsAndChangeTurn(listOfWordsTwoCorrect)
    }
    //endregion

}