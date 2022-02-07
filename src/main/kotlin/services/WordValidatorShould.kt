package services

import com.example.services.WordValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WordValidatorShould {
    private val sut = WordValidator()

    @Test
    fun `validate a correct word and return true`() {

        val result = sut.Validate("ANIMALS", "A")

        assertTrue(result)
    }

    @Test
    fun `validate an incorrect word and return false`() {

        val result = sut.Validate("ANIMALS", "AS")

        assertFalse(result)
    }

}