package com.example.dao

import com.example.model.Match

data class MatchToSend(var match:Match) {
    var matchid:Int = match.id
    var playerAID:Int = match.playerAID
    var playerBID:Int? = match.playerBID
    var rounds:MutableList<RoundToSend> = mutableListOf(RoundToSend(match.rounds[0]), RoundToSend(match.rounds[1]), RoundToSend(match.rounds[2]))
    var winner:Int? = match.winner
}