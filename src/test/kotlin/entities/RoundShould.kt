package entities

import com.example.match.domain.IMatchDependencies
import com.example.match.infrastructure.MatchTestDependencies
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
    private lateinit var listOfWordsPlayerA: MutableList<String>
    private lateinit var listOfWordsPlayerB: MutableList<String>

    @BeforeEach
    fun setup(){

        val matchTestDependencies: IMatchDependencies = MatchTestDependencies()

        val mockLetterRandomizerDependency = Mockito.mock(LetterRandomizer::class.java)
        Mockito.`when`(mockLetterRandomizerDependency.getRandomLetter()).thenReturn('A')

        val topicRandomizer = TopicRandomizer(matchTestDependencies.topicLoader)
        val wordValidator = ValidateWords(matchTestDependencies.topicLoader)

        val rounds = RoundFactory(topicRandomizer, mockLetterRandomizerDependency, wordValidator).getRounds(3)
        firstRound = rounds[0]
        secondRound = rounds[1]
        listOfWordsPlayerA = mutableListOf("A", "B", "A", "B", "A")
        listOfWordsPlayerB = mutableListOf("B", "B", "A", "B", "A")
    }

    @Test
    fun `have a letter`() {
        val result = firstRound.letter

        assertTrue(result.isLetter())
    }

    @Test
    fun `have five Topics`() {
        assertEquals(5,firstRound.readTopics().count())
    }

    @Test
    fun `add playerA words the first time adding words on first turn`() {
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerA)
        val result = firstRound.playerAWords

        assertEquals(listOfWordsPlayerA, result)
    }

    @Test
    fun `validate playerA words after adding them on first turn`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(true, false, true, false, true)
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerA)

        val result = firstRound.playerAWordsValidation

        assertEquals(expectedBoolList, result)
    }

    @Test
    fun `add playerB words the first time adding words on second turn`() {
        secondRound.addWordsAndChangeTurn(listOfWordsPlayerB)
        val result = secondRound.playerBWords

        assertEquals(listOfWordsPlayerB, result)
    }

    @Test
    fun `validate playerB words after adding them on second turn`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(false, false, true, false, true)
        secondRound.addWordsAndChangeTurn(listOfWordsPlayerB)

        val result = secondRound.playerBWordsValidation

        assertEquals(expectedBoolList, result)
    }


    @Test
    fun `Change To Second Turn After Adding First Words`() {
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerA)

        val result = firstRound.turn

        assertEquals(Turn.SECOND, result)
    }

    @Test
    fun `assign playerB words the second time adding words on first turn`() {
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerA)
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerB)
        val result = firstRound.playerBWords

        assertEquals(listOfWordsPlayerB, result)
    }

    @Test
    fun `validate playerB words after adding them on first turn`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(false, false, true, false, true)
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerA)
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerB)

        val result = firstRound.playerBWordsValidation

        assertEquals(expectedBoolList, result)
    }

    @Test
    fun `assign playerA words the second time adding words on second turn`() {
        secondRound.addWordsAndChangeTurn(listOfWordsPlayerB)
        secondRound.addWordsAndChangeTurn(listOfWordsPlayerA)
        val result = secondRound.playerAWords

        assertEquals(listOfWordsPlayerA, result)
    }

    @Test
    fun `validate playerA words after adding them on second turn`() {
        val expectedBoolList: MutableList<Boolean> = mutableListOf(true, false, true, false, true)
        secondRound.addWordsAndChangeTurn(listOfWordsPlayerB)
        secondRound.addWordsAndChangeTurn(listOfWordsPlayerA)

        val result = secondRound.playerAWordsValidation

        assertEquals(expectedBoolList, result)
    }

    @Test
    fun `change to turn to finished after adding second set of words`() {
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerA)
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerB)

        val result = firstRound.turn

        assertEquals(Turn.FINISHED, result)
    }

    @Test
    fun `calculate score when turn is over`() {
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerA)
        firstRound.addWordsAndChangeTurn(listOfWordsPlayerB)

        val result = firstRound.roundWinner

        assertEquals(RoundWinner.PLAYERA, result)
    }

}