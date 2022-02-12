package com.example.services

import com.example.DBConnection.HikariDBConnection

class DBMatchLoader {
    private val db = HikariDBConnection
    private val connection = db.getConnection()


}