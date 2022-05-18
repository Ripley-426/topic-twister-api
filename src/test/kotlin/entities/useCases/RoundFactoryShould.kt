package entities.useCases

import com.example.letterRandomizer.LetterRandomizer
import com.example.match.application.RoundFactory
import com.example.topic.application.TopicRandomizer
import com.example.wordValidator.application.ValidateWords
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class RoundFactoryShould {
    private lateinit var factory: RoundFactory

    @BeforeEach
    fun setup(){

        val topicRandomizerDependency = createTestDoubleTopicRandomizer()
        val letterRandomizerDependency = createTestDoubleLetterRandomizer()
        val validateWordsDependency = createTestDoubleValidateWords()

        factory = RoundFactory(topicRandomizerDependency, letterRandomizerDependency, validateWordsDependency)
    }

    private fun createTestDoubleLetterRandomizer() = Mockito.mock(LetterRandomizer::class.java)
    private fun createTestDoubleTopicRandomizer() = Mockito.mock(TopicRandomizer::class.java)
    private fun createTestDoubleValidateWords() = Mockito.mock(ValidateWords::class.java)

    @Test
    fun `return as many rounds as we ask for`() {
        val result = getNumberOfRounds()

        assertEquals(5, result)
    }

    @Test
    fun `assign correct round numbers`(){
        val result = getThreeRounds()

        assertEquals(3, result[2].roundNumber)
    }

    private fun getThreeRounds() = factory.getRounds(3)

    private fun getNumberOfRounds() = factory.getRounds(5).size
}