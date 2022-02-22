package com.example.interfaces

import com.example.model.Match

interface IMatchLoader {
    fun saveMatch(matchToSave:Match)
    fun loadMatch(matchID: Int): Match
    fun addPlayerB(matchID: Int, playerBID: Int)
}