package com.example.match.domain

interface IRoundFactory {
    fun getRounds(numberOfRounds: Int):MutableList<Round>
}