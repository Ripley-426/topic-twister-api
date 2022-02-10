package com.example
import com.example.debugTools.AddTopicsToDB
import com.example.debugTools.WordValidatorJSON
import com.example.model.ValidationContainer
import com.example.services.TopicRandomizer
import com.example.services.WordValidator
import com.google.gson.Gson
import org.springframework.web.bind.annotation.*
import services.LetterRandomizer

@RestController
class APIController {

    var gson = Gson()
    private val validator = WordValidator()
    private val letterRandomizer = LetterRandomizer()
    private val topicRandomizer = TopicRandomizer()

    @GetMapping("/letterRandomizer")

    fun getLetter() : String {
        return letterRandomizer.getRandomLetter().toString()
    }

    @GetMapping("/wordValidator")

    fun getValidation(@RequestParam topic: String, @RequestParam word: String): Boolean {
        return validator.Validate(topic, word)
    }

    @GetMapping("/wordsValidator")

    fun getValidation(@RequestBody jsonString:String): MutableList<Boolean> {
        val validationContainer:ValidationContainer = gson.fromJson(jsonString, ValidationContainer::class.java)

        return validator.GetValidationResult(validationContainer)
    }

    @GetMapping("/randomTopics")

    fun getRandomTopics(@RequestParam numberOfTopics: Int) : List<String> {
        return topicRandomizer.GetRandomTopics(numberOfTopics)
    }

    @GetMapping("/debugTools/wordValidation")

    fun getValidationJsonExample(): String {
        val jsonCreator = WordValidatorJSON()
        return jsonCreator.GetJsonExample()
    }

    @GetMapping("/debugTools/populateTopics")

    fun populateTopicsInDB() {
        val addTopicsToDB = AddTopicsToDB()
        addTopicsToDB.run()
    }
}