package com.example.services


class AddPlayerBToMatch {
    private val matchLoader = DBMatchLoader()


    fun addPlayerBToMatch(matchID: Int, playerBID: Int) {
        matchLoader.addPlayerB(matchID, playerBID)
    }
}