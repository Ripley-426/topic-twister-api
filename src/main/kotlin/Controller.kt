package com.example
import com.example.dao.MatchToSend
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

    private var gson = Gson()
    private val topicLoaderDependency = DBTopicLoader()
    private val validator = WordValidator(topicLoaderDependency)
    private val letterRandomizer = LetterRandomizer()
    private val topicRandomizer = TopicRandomizer(topicLoaderDependency)
    private val matchLoader = DBMatchLoader()

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
        matchLoader.createTableOnDB()
    }

    @GetMapping("match/GetMatchWithID")
    fun getMatchWithID(@RequestParam matchID: Int): String {
        val loadedMatch = matchLoader.loadMatch(matchID)
        val matchToSend = MatchToSend()
        matchToSend.convertMatch(loadedMatch)
        return gson.toJson(matchToSend)
    }

    @PostMapping("/match/addWordsToMatch")
    fun addWordsToMatch(@RequestParam matchID:Int, @RequestParam playerID:Int, @RequestParam words:String): String {
        val addWordsToMatch = AddWordsToMatch()
        addWordsToMatch.addWords(playerID, matchID, words)

        val loadedMatch = matchLoader.loadMatch(matchID)
        val matchToSend = MatchToSend()
        matchToSend.convertMatch(loadedMatch)
        return gson.toJson(matchToSend)
    }

    @PostMapping("/match/addPlayerBToMatch")
    fun addPlayerBToMatch(@RequestParam matchID:Int, @RequestParam playerID:Int) {
        val addPlayerBToMatch = AddPlayerBToMatch()
        addPlayerBToMatch.addPlayerBToMatch(matchID, playerID)
    }

    @GetMapping("match/GetMatchesOfPlayer")
    fun getMatchOfPlayer(@RequestParam playerID: Int): String {
        val matches = matchLoader.loadAllMatchesFromPlayer(playerID)
        val matchesList = mutableListOf<MatchToSend>()

        matches.forEach {
            val matchToSend = MatchToSend()
            matchToSend.convertMatch(it)
            matchesList.add(matchToSend)
        }

        return gson.toJson(matchesList)
    }

    @GetMapping("/match/startNewMatch")
    fun startNewMatch(@RequestParam playerID:Int): String {

        return gson.toJson(matchLoader.getFirstMatchWithoutPlayerB(playerID))
    }

}