package entities.useCases

import com.example.wordValidator.domain.TopicAndWord
import com.example.wordValidator.domain.WordValidator
import com.example.match.infrastructure.InMemoryTopicLoader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WordValidatorShould {

    private val topicLoaderDependency = InMemoryTopicLoader()
    private val sut = WordValidator(topicLoaderDependency)

    @Test
    fun `validate a correct word and return true`() {

        val result = sut.validate("ANIMALS", "ANACONDA")

        assertTrue(result)
    }

    @Test
    fun `validate an incorrect word and return false`() {

        val result = sut.validate("ANIMALS", "ANACOND")

        assertFalse(result)
    }

    @Test
    fun `return false when topic does not exist`() {

        val result = sut.validate("AAAAAA", "AAAAAA")
    }

    @Test
    fun `return false when a word has a different starting letter`() {
        val topicAndWord= TopicAndWord("ANIMALS", "ANACONDA")
        val letter = 'B'

        val result = sut.validateAnswer(topicAndWord, letter)

        assertFalse(result)
    }

}