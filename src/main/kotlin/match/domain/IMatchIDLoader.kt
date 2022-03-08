package com.example.match.domain

interface IMatchIDLoader {
    fun getID(): Int
    fun resetID()
}