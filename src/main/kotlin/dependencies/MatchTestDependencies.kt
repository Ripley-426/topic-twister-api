package com.example.dependencies

import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.IMatchIDLoader
import com.example.interfaces.ITopicLoader
import com.example.testServices.InMemoryMatchIDLoader
import com.example.testServices.InMemoryTopicLoader
import services.LetterRandomizer

class MatchTestDependencies: IMatchDependencies {
    override var matchIDLoader: IMatchIDLoader = InMemoryMatchIDLoader()
    override var letterRandomizer: ILetterRandomizer = LetterRandomizer()
    override var topicLoader: ITopicLoader = InMemoryTopicLoader()
}