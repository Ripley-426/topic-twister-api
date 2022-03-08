package com.example.services

import com.example.interfaces.IMatchLoader


class AddPlayerBToMatch {
    private val matchLoader:IMatchLoader = DBMatchLoader()

    fun addPlayerBToMatch(matchID: Int, playerBID: Int) {
        matchLoader.addPlayerB(matchID, playerBID)
    }
}