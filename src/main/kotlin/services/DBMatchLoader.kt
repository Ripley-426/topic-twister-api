package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.debugTools.StatementBuilderPostgresql
import com.example.model.DBMatch
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
        val match = stmt.executeQuery("SELECT * FROM MATCH WHERE IDMATCH = $matchid")
        match.next()
        val rounds = stmt.executeQuery("SELECT * FROM ROUND WHERE MATCHID = $matchid")
        rounds.next()
        val round1Letter = rounds.getString("letter")
        val round1Topics = rounds.getString("topics").split(",").toList()
        val round1PlayerAWords = rounds.getString("playerawords").split(",").toList()
        val round1PlayerBWords = rounds.getString("playerawordsvalidation").split(",").toList()
        val round1PlayerAWordsValidation =
            convertStringListToBooleanList(rounds.getString("playerbwords").split(",").toList())
        val round1PlayerBWordsValidation =
            convertStringListToBooleanList(rounds.getString("playerbwordsvalidation").split(",").toList())
        val round1Turn = rounds.getInt("turn")
        val round1Winner = rounds.getInt("roundwinner")
        rounds.next()
        val round2Letter = rounds.getString("letter")
        val round2Topics = rounds.getString("topics").split(",").toList()
        val round2PlayerAWords = rounds.getString("playerawords").split(",").toList()
        val round2PlayerBWords = rounds.getString("playerawordsvalidation").split(",").toList()
        val round2PlayerAWordsValidation =
            convertStringListToBooleanList(rounds.getString("playerbwords").split(",").toList())
        val round2PlayerBWordsValidation =
            convertStringListToBooleanList(rounds.getString("playerbwordsvalidation").split(",").toList())
        val round2Turn = rounds.getInt("turn")
        val round2Winner = rounds.getInt("roundwinner")
        rounds.next()
        val round3Letter = rounds.getString("letter")
        val round3Topics = rounds.getString("topics").split(",").toList()
        val round3PlayerAWords = rounds.getString("playerawords").split(",").toList()
        val round3PlayerBWords = rounds.getString("playerawordsvalidation").split(",").toList()
        val round3PlayerAWordsValidation =
            convertStringListToBooleanList(rounds.getString("playerbwords").split(",").toList())
        val round3PlayerBWordsValidation =
            convertStringListToBooleanList(rounds.getString("playerbwordsvalidation").split(",").toList())
        val round3Turn = rounds.getInt("turn")
        val round3Winner = rounds.getInt("roundwinner")

        return DBMatch(
            match.getInt("idmatch"),
            match.getInt("playeraid"),
            match.getInt("playerbid"),
            match.getInt("winner"),
            round1Letter[0], round2Letter[0], round3Letter[0],
            round1Topics, round2Topics, round3Topics,
            round1PlayerAWords, round2PlayerAWords, round3PlayerAWords,
            round1PlayerBWords, round2PlayerBWords, round3PlayerBWords,
            round1PlayerAWordsValidation, round2PlayerAWordsValidation, round3PlayerAWordsValidation,
            round1PlayerBWordsValidation, round2PlayerBWordsValidation, round3PlayerBWordsValidation,
            round1Turn, round2Turn, round3Turn,
            round1Winner, round2Winner, round3Winner
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