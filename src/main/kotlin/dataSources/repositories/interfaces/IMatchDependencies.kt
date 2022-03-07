package com.example.dataSources.repositories.interfaces

import com.example.entities.interfaces.ILetterRandomizer
import com.example.dataSources.repositories.interfaces.IMatchIDLoader
import com.example.dataSources.repositories.interfaces.ITopicLoader

interface IMatchDependencies {
    var matchIDLoader: IMatchIDLoader
    var letterRandomizer: ILetterRandomizer
    var topicLoader: ITopicLoader
}