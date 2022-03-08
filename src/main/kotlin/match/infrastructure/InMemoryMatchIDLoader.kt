package com.example.match.infrastructure

import com.example.match.domain.IMatchIDLoader

class InMemoryMatchIDLoader: IMatchIDLoader {

    private var id = 0
    override fun getID(): Int {
        id++
        return id
    }

    override fun resetID() { id = 0}
}