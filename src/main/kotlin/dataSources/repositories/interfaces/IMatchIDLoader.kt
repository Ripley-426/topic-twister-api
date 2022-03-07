package com.example.dataSources.repositories.interfaces

interface IMatchIDLoader {
    fun getID(): Int
    fun resetID()
}