package com.example.tempPermanence

import com.example.interfaces.IMatchIDLoader

class InMemoryMatchIDLoader: IMatchIDLoader {

    private var id = 0
    override fun getID(): Int {
        id++
        return id
    }

    override fun resetID() {}
}