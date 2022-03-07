package com.example
import com.example.dao.MatchToSend
import com.example.dataSources.repositories.DBMatchLoader
import com.example.entities.useCases.AddWordsToMatch
import com.google.gson.Gson
import org.springframework.web.bind.annotation.*

@RestController
class APIController {

    private var gson = Gson()
    private val matchLoader = DBMatchLoader()

    @GetMapping("match/GetMatchesOfPlayer")
    fun getMatchOfPlayer(@RequestParam playerID: Int): String {
        val matches = matchLoader.loadAllMatchesFromPlayer(playerID)
        val matchesList = mutableListOf<MatchToSend>()

        matches.forEach {
            matchesList.add(MatchToSend(it))
        }

        return gson.toJson(matchesList)
    }

    @GetMapping("/match/startNewMatch")
    fun startNewMatch(@RequestParam playerID:Int): String {

        return gson.toJson(matchLoader.getNewMatch(playerID))
    }

    @GetMapping("/match/startRematch")
    fun startRematch(@RequestParam playerID:Int, @RequestParam opponentID:Int): String {

        return gson.toJson(matchLoader.getRematch(playerID, opponentID))
    }

    @PostMapping("/match/addWordsToMatch")
    fun addWordsToMatch(@RequestParam matchID:Int, @RequestParam playerID:Int, @RequestParam words:String): String {
        val addWordsToMatch = AddWordsToMatch()
        addWordsToMatch.addWords(playerID, matchID, words)

        val loadedMatch = matchLoader.loadMatch(matchID)
        val matchToSend = MatchToSend(loadedMatch)
        return gson.toJson(matchToSend)
    }

}