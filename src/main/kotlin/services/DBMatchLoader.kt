package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.debugTools.StatementBuilderPostgresql
import com.example.dao.DBMatch
import com.example.dao.MatchToSend
import com.example.dependencies.MatchDBDependencies
import com.example.dto.LoadedMatch
import com.example.interfaces.IMatchLoader
import com.example.model.Match
import com.example.model.Round
import java.sql.ResultSet

class DBMatchLoader: IMatchLoader {
    private val stmt = HikariDBConnection.getConnection().createStatement()
    private val stmtBuilder = StatementBuilderPostgresql()
    private lateinit var match:Match

    override fun saveMatch(matchToSave:Match) {
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

    override fun loadMatch(matchID: Int): Match {
        val dbMatch = stmt.executeQuery("SELECT * FROM MATCH INNER JOIN ROUND ON ROUND.matchid " +
                " = MATCH.idmatch WHERE MATCH.idmatch = $matchID")

        dbMatch.next()

        return createLoadedMatchFromDBObject(dbMatch)

    }

    fun loadAllMatchesFromPlayer(playerID: Int): MutableList<Match> {
        var matchesList = mutableListOf<Match>()

        val dbMatch = stmt.executeQuery("SELECT * FROM MATCH INNER JOIN ROUND ON ROUND.matchid " +
                " = MATCH.idmatch WHERE MATCH.playeraid = $playerID OR MATCH.playerbid = $playerID")

        while (dbMatch.next()) {
            matchesList.add(createLoadedMatchFromDBObject(dbMatch))
        }

        return matchesList
    }

    fun getFirstMatchWithoutPlayerB(playerID: Int): MatchToSend {

        val dbMatch = stmt.executeQuery("SELECT * FROM MATCH INNER JOIN ROUND ON ROUND.matchid = MATCH.idmatch " +
                "WHERE MATCH.playerbid = 0 AND MATCH.playeraid != $playerID")

        dbMatch.next()

        val matchToSend:MatchToSend = if (dbMatch.row == 1) {

            val match = createLoadedMatchFromDBObject(dbMatch)

            addPlayerB(match.id, playerID)

            val updatedMatch = loadMatch(match.id)

            MatchToSend(updatedMatch)
        } else {

            val startNewMatch = StartNewMatch()

            startNewMatch.createNewMatch(playerID)
        }

        return matchToSend
    }

    fun createLoadedMatchFromDBObject(dbMatch: ResultSet): LoadedMatch {

        val matchid = dbMatch.getInt("idmatch")
        val playeraid =  dbMatch.getInt("playeraid")
        val playerbid = dbMatch.getInt("playerbid")
        val winner = dbMatch.getInt("winner")
        val round1Letter = dbMatch.getString("letter")[0]
        val round1Topics = dbMatch.getString("topics").split(",").toList()
        val round1PlayerAWords = dbMatch.getString("playerawords").split(",").toList()
        val round1PlayerBWords = dbMatch.getString("playerbwords").split(",").toList()
        val round1PlayerAWordsValidation = convertStringListToBooleanList(dbMatch.getString("playerawordsvalidation").split(",").toList())
        val round1PlayerBWordsValidation = convertStringListToBooleanList(dbMatch.getString("playerbwordsvalidation").split(",").toList())
        val round1Turn = dbMatch.getInt("turn")
        val round1Winner = dbMatch.getInt("roundwinner")

        dbMatch.next()

        val round2Letter = dbMatch.getString("letter")[0]
        val round2Topics = dbMatch.getString("topics").split(",").toList()
        val round2PlayerAWords = dbMatch.getString("playerawords").split(",").toList()
        val round2PlayerBWords = dbMatch.getString("playerbwords").split(",").toList()
        val round2PlayerAWordsValidation = convertStringListToBooleanList(dbMatch.getString("playerawordsvalidation").split(",").toList())
        val round2PlayerBWordsValidation = convertStringListToBooleanList(dbMatch.getString("playerbwordsvalidation").split(",").toList())
        val round2Turn = dbMatch.getInt("turn")
        val round2Winner = dbMatch.getInt("roundwinner")

        dbMatch.next()

        val round3Letter = dbMatch.getString("letter")[0]
        val round3Topics = dbMatch.getString("topics").split(",").toList()
        val round3PlayerAWords = dbMatch.getString("playerawords").split(",").toList()
        val round3PlayerBWords = dbMatch.getString("playerbwords").split(",").toList()
        val round3PlayerAWordsValidation = convertStringListToBooleanList(dbMatch.getString("playerawordsvalidation").split(",").toList())
        val round3PlayerBWordsValidation = convertStringListToBooleanList(dbMatch.getString("playerbwordsvalidation").split(",").toList())
        val round3Turn = dbMatch.getInt("turn")
        val round3Winner = dbMatch.getInt("roundwinner")

        val dbDependencies = MatchDBDependencies()

        return LoadedMatch (playeraid, dbDependencies.matchIDLoader, dbDependencies.letterRandomizer, dbDependencies.topicLoader, matchid, playerbid,
            winner, round1Letter, round1Topics, round1PlayerAWords, round1PlayerBWords, round1PlayerAWordsValidation,
            round1PlayerBWordsValidation, round1Turn, round1Winner,
            round2Letter, round2Topics, round2PlayerAWords, round2PlayerBWords, round2PlayerAWordsValidation,
            round2PlayerBWordsValidation, round2Turn, round2Winner,
            round3Letter, round3Topics, round3PlayerAWords, round3PlayerBWords, round3PlayerAWordsValidation,
            round3PlayerBWordsValidation, round3Turn, round3Winner
        )
    }

    override fun updateMatch(matchToUpdate: Match) {
        match = matchToUpdate
        updateMatchTable()
    }

    override fun getRematch(playerID: Int, opponentID: Int): MatchToSend {
        val startRematch = StartNewMatch()
        val rematch = startRematch.createNewMatch(playerID)
        addPlayerB(rematch.matchid, opponentID)
        val updatedRematch = loadMatch(rematch.matchid)
        return MatchToSend(updatedRematch)
    }

    override fun addPlayerB(matchID: Int, playerID: Int) {
        stmt.executeUpdate(stmtBuilder.updateIntoTablesThisMapping("MATCH", "idmatch = $matchID",
        mapOf(
            Pair("playerBID", convertToDBInt(playerID))
            )
        ))
    }

    fun loadDBMatchFromDB(matchid: Int): DBMatch {
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

    private fun updateMatchTable() {
        stmt.executeUpdate(stmtBuilder.updateIntoTablesThisMapping("MATCH", "idmatch = " + convertToDBInt(match.id),
            mapOf(
                Pair("playerAID", convertToDBInt(match.playerAID)),
                Pair("playerBID", convertToDBInt(match.playerBID)),
                Pair("winner", convertToDBInt(match.winner))
            )
        ))

        updateRounds()
    }

    private fun updateRounds() {
        match.rounds.forEach { updateRoundTable(it, match.id)}
    }

    private fun updateRoundTable(round: Round, matchID: Int) {
        stmt.executeUpdate(stmtBuilder.updateIntoTablesThisMapping( "ROUND",
            "matchid = " + convertToDBInt(matchID) + " AND idround = " + convertToDBInt(round.roundNumber),
            mapOf(
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
        val newList = mutableListOf<Boolean>()
        originalList.forEach { if (it == "true") { newList.add(true)} else { newList.add(false)} }
        return newList.toList()
    }
}