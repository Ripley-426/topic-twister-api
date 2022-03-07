package model

import com.example.dataSources.repositories.interfaces.IMatchDependencies
import com.example.dataSources.repositories.MatchTestDependencies
import com.example.entities.Round
import com.example.entities.enumClasses.RoundWinner
import com.example.entities.enumClasses.Turn
import com.example.entities.useCases.TopicRandomizer
import com.example.entities.useCases.WordValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import com.example.entities.useCases.LetterRandomizer

class RoundShould {

    private lateinit var firstRound: Round
    private lateinit var listOfWordsPlayerA: MutableList<String>
    private lateinit var listOfWordsPlayerB: MutableList<String>

    @BeforeEach
    fun setup(){

        val matchTestDependencies: IMatchDependencies = MatchTestDependencies()

        val mockLetterRandomizerDependency = Mockito.mock(LetterRandomizer::class.java)
        Mockito.`when`(mockLetterRandomizerDependency.getRandomLetter()).thenReturn('A')

        val topicRandomizer = TopicRandomizer(matchTestDependencies.topicLoader)
        val wordValidator = WordValidator(matchTestDependencies.topicLoader)

        firstRound = Round(1, topicRandomizer, mockLetterRandomizerDependency, wordValidator)
        listOfWordsPlayerA = mutableListOf("A", "B", "A", "B", "A")
        listOfWordsPlayerB = mutableListOf("B", "B", "A", "B", "A")
    }

    @Test
    fun `have a letter`() {

        //When
        val result = firstRound.letter

        //Then
        assertTrue(result.isLetter())
    }

    @Test
    fun `have five Topics`() {

        assertEquals(5,firstRound.readTopics().count())
    }

    @Test
    fun `add playerA words`() {

        firstRound.addWords(listOfWordsPlayerA)
        val result = firstRound.playerAWords


        assertEquals(listOfWordsPlayerA, result)
    }

    @Test
    fun `validate playerA words after adding them`() {

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
    fun `assign playerB words the second time adding words`() {

        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)
        val result = firstRound.playerBWords

        assertEquals(listOfWordsPlayerB, result)
    }

    @Test
    fun `validate playerB words after adding them`() {

        val expectedBoolList: MutableList<Boolean> = mutableListOf(false, false, true, false, true)
        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)

        val result = firstRound.playerBWordsValidation

        assertEquals(expectedBoolList, result)
    }

    @Test
    fun `change to turn to finished after adding second set of words`() {

        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)

        val result = firstRound.turn

        assertEquals(Turn.FINISHED, result)
    }

    @Test
    fun `calculate score when turn is over`() {

        firstRound.addWords(listOfWordsPlayerA)
        firstRound.addWords(listOfWordsPlayerB)

        val result = firstRound.roundWinner

        assertEquals(RoundWinner.PLAYERA, result)
    }

}