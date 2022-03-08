package com.example.match.infrastructure

import com.example.match.infrastructure.dataSources.HikariDBConnection
import com.example.match.domain.IMatchIDLoader

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