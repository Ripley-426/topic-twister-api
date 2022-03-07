package com.example.dataSources.repositories.interfaces

import com.example.dao.MatchToSend
import com.example.entities.Match

interface IMatchLoader {
    fun saveMatch(matchToSave: Match)
    fun loadMatch(matchID: Int): Match
    fun addPlayerB(matchID: Int, playerID: Int)
    fun updateMatch(matchToUpdate: Match)
    fun getRematch(playerID: Int, opponentID: Int): MatchToSend
}