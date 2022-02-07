package com.example
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

    @GetMapping("/letterRandomizer")

    fun getLetter() : String {
        val randomizer = LetterRandomizer()
        return randomizer.getRandomLetter()
    }

    @GetMapping("/wordValidator")

    fun getValidation(@RequestParam topic: String, @RequestParam word: String): Boolean {
        val validator = WordValidator()
        return validator.Validate(topic, word)
    }

    @GetMapping("/wordsValidator")

    fun getValidation(@RequestBody jsonString:String): MutableList<Boolean> {
        val validationContainer:ValidationContainer = gson.fromJson(jsonString, ValidationContainer::class.java)

        val validator = WordValidator()

        return validator.GetValidationResult(validationContainer)
    }

    @GetMapping("/randomTopics")

    fun getRandomTopics(@RequestParam numberOfTopics: Int) : List<String> {
        val randomizer = TopicRandomizer()
        return randomizer.GetRandomTopics(numberOfTopics)
    }

    @GetMapping("/debugTools/wordValidation")

    fun getValidationJsonExample(): String {
        val jsonCreator = WordValidatorJSON()
        return jsonCreator.GetJsonExample()
    }
}