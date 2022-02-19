package model

import com.example.dependencies.TestDependencies
import com.example.model.Round
import com.example.enumClasses.RoundWinner
import com.example.enumClasses.Turn
import com.example.services.TopicRandomizer
import com.example.services.WordValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import services.LetterRandomizer

class RoundShould {

    private lateinit var firstRound: Round
    private lateinit var listOfWordsPlayerA: MutableList<String>
    private lateinit var listOfWordsPlayerB: MutableList<String>

    @BeforeEach
    fun setup(){

        val testDependencies = TestDependencies()

        val mockLetterRandomizerDependency = Mockito.mock(LetterRandomizer::class.java)
        Mockito.`when`(mockLetterRandomizerDependency.getRandomLetter()).thenReturn('A')

        val topicRandomizer = TopicRandomizer(testDependencies.topicLoader)
        val wordValidator = WordValidator(testDependencies.topicLoader)

        firstRound = Round(1, topicRandomizer, mockLetterRandomizerDependency, wordValidator)
        listOfWordsPlayerA = mutableListOf("A", "B", "A", "B", "A")
        listOfWordsPlayerB = mutableListOf("B", "B", "A", "B", "A")
    }

    @Test
    fun `Have A Letter`() {

        //When
        val result = firstRound.letter

        //Then
        assertTrue(result.isLetter())
    }

    @Test
    fun `Have Topics`() {

        assertEquals(5,firstRound.readTopics().count())
    }

    @Test
    fun `Add Player A Words`() {

        firstRound.addWords(listOfWordsPlayerA)
        val result = firstRound.playerAWords


        assertEquals(listOfWordsPlayerA, result)
    }

    @Test
    fun `Validate Player A Words After Adding Them`() {

        val expectedBoolList: MutableList<Boolean> = mutableListOf(true, false, true, false, true)
        firstRound.addWords(listOfWordsPlayerA)

        val result = firstRound.playerAWordsValidation

        assertEquals(expectedBoolList, result)
    }


    @Test
    fun `Change To Second Turn After Adding First Words`() {

        firstRound.addWords(listOfWordsPlayerA)

        val result = firstRound.turn

        assertEquals(Turn.SECOND, result)
    }

    @Test
    fun `Add Player B Words`() {

        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)
        val result = firstRound.playerBWords

        assertEquals(listOfWordsPlayerB, result)
    }

    @Test
    fun `Validate Player B Words After Adding Them`() {

        val expectedBoolList: MutableList<Boolean> = mutableListOf(false, false, true, false, true)
        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)

        val result = firstRound.playerBWordsValidation

        assertEquals(expectedBoolList, result)
    }

    @Test
    fun `Change To Finished Turn After Adding Second Words`() {

        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)

        val result = firstRound.turn

        assertEquals(Turn.FINISHED, result)
    }

    @Test
    fun `Calculate Score When Turn is Over`() {

        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)

        val result = firstRound.roundWinner

        assertEquals(RoundWinner.PLAYERA, result)
    }

}