package com.example.dataSources.repositories

import com.example.dataSources.repositories.interfaces.IMatchIDLoader

class InMemoryMatchIDLoader: IMatchIDLoader {

    private var id = 0
    override fun getID(): Int {
        id++
        return id
    }

    override fun resetID() { id = 0}
}