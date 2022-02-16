package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.debugTools.StatementBuilderPostgresql
import com.example.model.Match
import com.example.model.Round

class DBMatchLoader {
    private val stmt = HikariDBConnection.getConnection().createStatement()
    private val stmtBuilder = StatementBuilderPostgresql()
    private lateinit var match:Match

    fun saveMatchToDB(matchToSave:Match) {
        match = matchToSave
        saveMatchTable()
    }

    private fun saveMatchTable() {
        stmt.executeUpdate(stmtBuilder.insertIntoTablesThisMapping( "MATCH",
            mapOf(
            Pair("idMatch", convertToDBInt(match.id)),
            Pair("playerAID", convertToDBInt(match.playerAID)),
            Pair("playerBID", convertToDBInt(match.playerBID)),
            Pair("winner", convertToDBInt(match.winner))
            )
        ))

        saveRounds()
    }

    private fun saveRounds() {
        match.rounds.forEach { saveRoundTable(it, match.id)}
    }

    private fun saveRoundTable(round: Round, matchID: Int) {
        stmt.executeUpdate(stmtBuilder.insertIntoTablesThisMapping( "ROUND",
            mapOf(
            Pair("idRound",  convertToDBInt(round.roundNumber)),
            Pair("matchID", convertToDBInt(matchID)),
            Pair("topics", convertToDBText(round.topics.joinToString(separator = ","))),
            Pair("playerAWords", convertToDBText(round.playerAWords.joinToString(separator = ","))),
            Pair("playerAWordsValidation", convertToDBText(round.playerAWordsValidation.joinToString (separator = ","))),
            Pair("playerBWords", convertToDBText(round.playerBWords.joinToString(separator = ","))),
            Pair("playerBWordsValidation", convertToDBText(round.playerBWordsValidation.joinToString(separator = ","))),
            Pair("turn", convertToDBInt(round.getTurnInt())),
            Pair("roundWinner", convertToDBInt(round.getWinnerInt()))
            )
        ))
    }

    fun createTableOnDB() {
        createPlayerTable()
        createMatchTable()
        createRoundTable()
    }

    private fun createPlayerTable() {
        stmt.executeUpdate(stmtBuilder.createTableIfExists("PLAYER",
            "idPlayer int, name text, email text, passWord text"))
    }

    private fun createMatchTable() {
        stmt.executeUpdate(stmtBuilder.createTableIfExists("MATCH",
        "idMatch int, playerAID int, playerBID int, winner int"))
    }

    private fun createRoundTable() {
        stmt.executeUpdate(stmtBuilder.createTableIfExists("ROUND",
        "idRound int, matchID int, topics text, playerAWords text, playerAWordsValidation text, " +
                "playerBWords text, playerBWordsValidation text, turn int, roundWinner int"))
    }

    private fun convertToDBText(value:String):String {
        return "'$value'"
    }

    private fun convertToDBInt(value: Int?):String {
        return value.toString()
    }
}