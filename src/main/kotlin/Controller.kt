package com.example
import com.example.debugTools.AddTestMatch
import com.example.debugTools.AddTopicsToDB
import com.example.debugTools.WordValidatorJSON
import com.example.interfaces.IMatchIDLoader
import com.example.model.ValidationContainer
import com.example.services.*
import com.google.gson.Gson
import org.springframework.web.bind.annotation.*
import services.LetterRandomizer

@RestController
class APIController {

    var gson = Gson()
    private val topicLoaderDependency = DBTopicLoader()
    private val validator = WordValidator(topicLoaderDependency)
    private val letterRandomizer = LetterRandomizer()
    private val topicRandomizer = TopicRandomizer(topicLoaderDependency)

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

        return validator.getValidationResult(validationContainer)
    }

    @GetMapping("/randomTopics")

    fun getRandomTopics(@RequestParam numberOfTopics: Int) : List<String> {
        return topicRandomizer.getRandomTopics(numberOfTopics)
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

    @PostMapping("/debugTools/addTestMatch")

    fun addTestMatch() {
        val addTestMatch = AddTestMatch()
        addTestMatch.saveTestMatch()
    }

    @GetMapping("match/getMatchID")
    fun getMatchID(): Int {
        val matchIDLoader: IMatchIDLoader = DBMatchIDLoader()
        return matchIDLoader.getID()
    }

    @GetMapping("match/resetMatchID")
    fun resetMatchID() {
        val matchIDLoader: IMatchIDLoader = DBMatchIDLoader()
        matchIDLoader.resetID()
    }

    @GetMapping("table/createTables")
    fun createTables() {
        val dbMatchLoader = DBMatchLoader()
        dbMatchLoader.createTableOnDB()
    }

}