package com.example.dataSources.repositories

import com.example.dataSources.HikariDBConnection
import com.example.dataSources.repositories.interfaces.IMatchIDLoader

class DBMatchIDLoader: IMatchIDLoader {

    private val db = HikariDBConnection
    private val stmt = db.getStatement()

    override fun getID(): Int {

        val dbID = stmt.executeQuery("SELECT id FROM MATCHID")

        dbID.next()
        val currentMatchID = dbID.getInt("id")

        val nextMatchID = currentMatchID + 1

        stmt.executeUpdate("UPDATE MATCHID SET id = '$nextMatchID'")

        return currentMatchID
    }

    override fun resetID() {
        stmt.executeUpdate("UPDATE MATCHID SET id = '1'")
    }
}