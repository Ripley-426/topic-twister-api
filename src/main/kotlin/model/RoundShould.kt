package model

import com.example.model.Round
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RoundShould {

    private lateinit var letter: String
    private lateinit var round: Round

    @BeforeEach
    fun setup(){
        letter = "A"
        round = Round(letter)
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
        round.addWords(mutableListOf("A", "B", "A", "B", "A"))


        val result = round.wordsValidations

        assertEquals(expectedBoolList, result)
    }


}