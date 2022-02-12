package com.example.services

import com.example.DBConnection.HikariDBConnection
import com.example.interfaces.IMatchIDLoader

class DBMatchIDLoader: IMatchIDLoader {

    private val db = HikariDBConnection
    private val connection = db.getConnection()

    override fun getID(): Int {

        val stmt = connection.createStatement()
        val dbID = stmt.executeQuery("SELECT id FROM MATCHID")

        dbID.next()
        val currentMatchID = dbID.getInt("id")

        val nextMatchID = currentMatchID + 1

        stmt.executeUpdate("UPDATE MATCHID SET id = '$nextMatchID'")

        return currentMatchID
    }
}