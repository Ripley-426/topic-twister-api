package model

import com.example.enumClasses.Turn
import com.example.model.Match
import com.example.model.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MatchShould {

    private lateinit var match: Match
    private lateinit var playerA: Player
    private lateinit var playerB: Player
    private lateinit var listOfWordsPlayerA: MutableList<String>
    private lateinit var listOfWordsPlayerB: MutableList<String>


    @BeforeEach
    fun setUp(){
        playerA = Player(1, "Juan")
        playerB = Player(2, "Pedro")
        match = Match(playerA)
        listOfWordsPlayerA = mutableListOf("A", "B", "A", "B", "A")
        listOfWordsPlayerB = mutableListOf("B", "B", "A", "B", "A")
    }

    @Test
    fun `Have three rounds when created`() {

        val result = match.rounds.count()

        assertEquals(3, result)
    }

    @Test
    fun `Add words to the correct round`() {

        match.addWords(listOfWordsPlayerA)

        val result = match.getCurrentRound().turn

        assertTrue(result == Turn.SECOND)
    }

    @Test
    fun `Cant add second set of words without playerB`() {
        match.addWords(listOfWordsPlayerA)

        val result = match.addWords(listOfWordsPlayerB)

        assertFalse(result)
    }

    @Test
    fun `Change turn when two set of words are added` () {

        match.addWords(listOfWordsPlayerA)
        match.addPlayerB(playerB)
        match.addWords(listOfWordsPlayerB)

        val result = match.getCurrentRound().roundNumber

        assertEquals(2, result)
    }

    /*
    @Test
    fun `Become complete when all sets of words are added` () {
        match.addWords(listOfWordsPlayerA)
        match.addWords(listOfWordsPlayerB)

        match.addWords(listOfWordsPlayerB)
        match.addWords(listOfWordsPlayerA)

        match.addWords(listOfWordsPlayerA)
        match.addWords(listOfWordsPlayerB)

        val result = match.winner

        assertNotNull(result)
    }
*/
    @Test
    fun `Calculate winner when match is completed` () {
        match.addWords(listOfWordsPlayerA)
        match.addPlayerB(playerB)
        match.addWords(listOfWordsPlayerB)

        match.addWords(listOfWordsPlayerB)
        match.addWords(listOfWordsPlayerA)

        match.addWords(listOfWordsPlayerA)
        match.addWords(listOfWordsPlayerB)

        val result = match.winner

        assertNotNull(result)
    }




}