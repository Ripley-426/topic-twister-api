package com.example
import com.example.services.WordValidator
import org.springframework.http.MediaType
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.web.bind.annotation.*
import services.LetterRandomizer
import java.net.URI

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
}