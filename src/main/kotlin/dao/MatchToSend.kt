package com.example.dao

import com.example.model.Match

data class MatchToSend(var matchid:Int = 0) {
    var playerAID:Int = 0
    var playerBID:Int = 0
    var rounds:MutableList<RoundToSend> = mutableListOf()
    var winner:Int = 0

    fun convertMatch(match: Match) {
        matchid = match.id
        playerAID = match.playerAID
        playerBID = match.playerBID!!
        winner = match.winner!!


        match.rounds.forEach {
            val roundToSend = RoundToSend()
            roundToSend.convertRound(it)
            rounds.add(roundToSend)
        }
    }
}