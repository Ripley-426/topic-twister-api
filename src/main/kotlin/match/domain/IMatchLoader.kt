package com.example.match.domain

import com.example.match.dao.MatchToSend

interface IMatchLoader {
    fun saveMatch(matchToSave: Match)
    fun loadMatch(matchID: Int): Match
    fun addPlayerB(matchID: Int, playerID: Int)
    fun updateMatch(matchToUpdate: Match)
    fun getRematch(playerID: Int, opponentID: Int): MatchToSend
}