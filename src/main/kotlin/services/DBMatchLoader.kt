package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.model.Match
import java.sql.Statement

class DBMatchLoader {
    private val db = HikariDBConnection
    private val connection = db.getConnection()

    fun saveMatchToDB(match:Match) {

    }

    fun createTableOnDB() {
        val stmt = connection.createStatement()
        createPlayerTable(stmt)
        createMatchTable(stmt)
        createRoundTable(stmt)
    }

    private fun createPlayerTable(stmt: Statement) {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PLAYER (" +
                "idPlayer int, " +
                "name string, " +
                "email text, " +
                "passWord text")
    }

    private fun createMatchTable(stmt: Statement) {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS MATCH (" +
                "idMatch int, " +
                "playerAID int, " +
                "playerBID int, " +
                "winner int, ")
    }

    private fun createRoundTable(stmt: Statement) {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ROUND (" +
                "idRound int, " +
                "matchID int, " +
                "topics text, " +
                "playerAWords text, " +
                "playerAWordsValidation text, " +
                "playerBWords text, " +
                "playerBWordsValidation text, " +
                "turn int, " +
                "roundWinner int, ")
    }
}