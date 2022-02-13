package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.model.Match

class DBMatchLoader {
    private val db = HikariDBConnection
    private val connection = db.getConnection()

    fun saveMatchToDB(match:Match) {

    }

    fun createTableOnDB() {
        val stmt = connection.createStatement()
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS MATCH (" +
                "id int, " +
                "playeraid int, " +
                "playeraname text, " +
                "playerbid int, " +
                "playerbname text, " +
                "winner int, " +
                "round1letter text, " +
                "round1topics text, " +
                "round1playerawords text, " +
                "round1playerbwords text, " +
                "round1playerawordsvalidation text, " +
                "round1playerbwordsvalidation text, " +
                "round1turn int, " +
                "round1winner int, " +
                "round2letter text, " +
                "round2topics text, " +
                "round2playerawords text, " +
                "round2playerbwords text, " +
                "round2playerawordsvalidation text, " +
                "round2playerbwordsvalidation text, " +
                "round2turn int, " +
                "round2winner int, " +
                "round3letter text, " +
                "round3topics text, " +
                "round3playerawords text, " +
                "round3playerbwords text, " +
                "round3playerawordsvalidation text, " +
                "round3playerbwordsvalidation text, " +
                "round3turn int, " +
                "round3winner int)")
    }
}