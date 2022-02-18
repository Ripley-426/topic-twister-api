package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.debugTools.StatementBuilderPostgresql
import com.example.dao.DBMatch
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

    fun getDBMatchFromDB(matchid: Int): DBMatch {
        val match = stmt.executeQuery("SELECT * FROM MATCH INNER JOIN ROUND ON ROUND.matchid " +
                " = MATCH.idmatch WHERE MATCH.idmatch = $matchid AND ROUND.idround = 1")
        match.next()

        val round1Letter = match.getString("letter")
        val round1Topics = match.getString("topics").split(",").toList()
        val round1PlayerAWords = match.getString("playerawords").split(",").toList()
        val round1PlayerBWords = match.getString("playerawordsvalidation").split(",").toList()
        val round1PlayerAWordsValidation =
            convertStringListToBooleanList(match.getString("playerbwords").split(",").toList())
        val round1PlayerBWordsValidation =
            convertStringListToBooleanList(match.getString("playerbwordsvalidation").split(",").toList())
        val round1Turn = match.getInt("turn")
        val round1Winner = match.getInt("roundwinner")

        return DBMatch(
            match.getInt("idmatch"),
            match.getInt("playeraid"),
            match.getInt("playerbid"),
            match.getInt("winner"),
            round1Letter[0],
            round1Topics,
            round1PlayerAWords,
            round1PlayerBWords,
            round1PlayerAWordsValidation,
            round1PlayerBWordsValidation,
            round1Turn,
            round1Winner,
        )
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
                Pair("letter", convertToDBText(round.letter.toString())),
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
        "idRound int, matchID int, letter text, topics text, playerAWords text, playerAWordsValidation text, " +
                "playerBWords text, playerBWordsValidation text, turn int, roundWinner int"))
    }

    private fun convertToDBText(value:String?):String {
        if (value == null) { return "NULL"}
        return "'$value'"
    }

    private fun convertToDBInt(value: Int?):String {
        if (value == null) { return "NULL"}
        return value.toString()
    }

    private fun convertStringListToBooleanList(originalList: List<String>): List<Boolean> {
        if (originalList[0] == "") { return listOf() }
        var newList = mutableListOf<Boolean>()
        originalList.forEach { if (it == "true") { newList.add(true)} else { newList.add(false)} }
        return newList.toList()
    }
}