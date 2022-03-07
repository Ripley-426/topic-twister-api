package com.example.dataSources.repositories

import com.example.entities.interfaces.ILetterRandomizer
import com.example.dataSources.repositories.interfaces.IMatchIDLoader
import com.example.dataSources.repositories.interfaces.ITopicLoader
import com.example.dataSources.repositories.interfaces.IMatchDependencies
import com.example.entities.useCases.LetterRandomizer

class MatchDBDependencies: IMatchDependencies {
    override var matchIDLoader: IMatchIDLoader = DBMatchIDLoader()
    override var letterRandomizer: ILetterRandomizer = LetterRandomizer()
    override var topicLoader: ITopicLoader = DBTopicLoader()
}