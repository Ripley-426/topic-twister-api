package com.example
import com.example.debugTools.WordValidatorJSON
import com.example.services.TopicRandomizer
import com.example.services.WordValidator
import org.springframework.web.bind.annotation.*
import services.LetterRandomizer

@RestController
class APIController {

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