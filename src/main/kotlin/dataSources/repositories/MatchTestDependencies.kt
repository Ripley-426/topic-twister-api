package com.example.dataSources.repositories

import com.example.entities.interfaces.ILetterRandomizer
import com.example.dataSources.repositories.interfaces.IMatchIDLoader
import com.example.dataSources.repositories.interfaces.ITopicLoader
import com.example.dataSources.repositories.interfaces.IMatchDependencies
import com.example.entities.useCases.LetterRandomizer

class MatchTestDependencies: IMatchDependencies {
    override var matchIDLoader: IMatchIDLoader = InMemoryMatchIDLoader()
    override var letterRandomizer: ILetterRandomizer = LetterRandomizer()
    override var topicLoader: ITopicLoader = InMemoryTopicLoader()
}