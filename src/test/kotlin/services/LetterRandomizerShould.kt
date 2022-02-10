package services

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LetterRandomizerShould {

    private var letterRandomizer:LetterRandomizer = LetterRandomizer()
    private var result:Char = letterRandomizer.getRandomLetter()

    @Test
    fun `return a letter`() {

        assertTrue(result.isLetter())

    }

    @Test
    fun `be uppercase`() {

        assertTrue(result.isUpperCase())

    }

}