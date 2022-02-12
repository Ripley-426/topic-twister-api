package services

import com.example.model.TopicAndWord
import com.example.services.WordValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WordValidatorShould {
    private val sut = WordValidator()

    @Test
    fun `validate a correct word and return true`() {

        val result = sut.Validate("ANIMALS", "ANACONDA")

        assertTrue(result)
    }

    @Test
    fun `validate an incorrect word and return false`() {

        val result = sut.Validate("ANIMALS", "ANACOND")

        assertFalse(result)
    }

    @Test
    fun `return false when topic does not exist`() {

        val result = sut.Validate("AAAAAA", "AAAAAA")
    }

    @Test
    fun `return false when a word has a different starting letter`() {
        val topicAndWord= TopicAndWord("ANIMALS", "ANACONDA")
        val letter = 'B'

        val result = sut.ValidateAnswer(topicAndWord, letter)

        assertFalse(result)
    }

}