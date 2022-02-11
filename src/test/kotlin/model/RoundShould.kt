package model

import com.example.model.Round
import com.example.model.Turn
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RoundShould {

    private lateinit var letter: String
    private lateinit var round: Round
    private lateinit var listOfWords: MutableList<String>

    @BeforeEach
    fun setup(){
        letter = "A"
        round = Round(letter)
        listOfWords = mutableListOf("A", "B", "A", "B", "A")
    }

    @Test
    fun `Have A Letter`() {

        //When
        val result = round.letter

        //Then
        assertEquals(letter, result)
    }

    @Test
    fun `Have Topics`() {

        assertEquals(5,round.readTopics().count())
    }

    @Test
    fun `Validate Words After Adding them`() {

        val expectedBoolList: MutableList<Boolean> = mutableListOf(true, false, true, false, true)
        round.addWords(listOfWords)


        val result = round.wordsValidations

        assertEquals(expectedBoolList, result)
    }


    @Test
    fun `Change To Second Turn After Adding Words`() {

        round.addWords(listOfWords)

        val result = round.turn

        assertEquals(Turn.SECOND, result)
    }

    @Test
    fun `Add Player A Words`() {

        round.addWords(listOfWords)
        val result = round.playerAWords


        assertEquals(listOfWords, result)
    }

    @Test
    fun `Add Player B Words`() {

        round.addWords(listOfWords)
        round.addWords(listOfWords)
        val result = round.playerBWords


        assertEquals(listOfWords, result)
    }

}