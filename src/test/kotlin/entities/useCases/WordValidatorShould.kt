package entities.useCases

import com.example.wordValidator.domain.TopicAndWord
import com.example.wordValidator.domain.WordValidator
import com.example.topic.infrastructure.InMemoryTopicLoader
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WordValidatorShould {

    private val topicLoaderDependency = InMemoryTopicLoader()
    private val wordValidator = WordValidator(topicLoaderDependency)

    @Test
    fun `validate a correct word and return true`() {
        val result = wordValidator.validate("ANIMALS", "ANACONDA")
        assertTrue(result)
    }

    @Test
    fun `validate an incorrect word and return false`() {
        val result = wordValidator.validate("ANIMALS", "ANACOND")
        assertFalse(result)
    }

    @Test
    fun `return false when topic does not exist`() {
        val result = wordValidator.validate("AAAAAA", "AAAAAA")
        assertFalse(result)
    }

    @Test
    fun `return false when a word has a different starting letter`() {
        val topicAndWord= TopicAndWord("ANIMALS", "ANACONDA")
        val letter = 'B'
        val result = wordValidator.validateAnswer(topicAndWord, letter)
        assertFalse(result)
    }

}