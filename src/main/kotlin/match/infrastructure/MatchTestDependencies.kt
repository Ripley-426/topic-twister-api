package com.example.match.infrastructure

import com.example.letterRandomizer.ILetterRandomizer
import com.example.match.domain.IMatchIDLoader
import com.example.topic.domain.ITopicLoader
import com.example.match.domain.IMatchDependencies
import com.example.letterRandomizer.LetterRandomizer

class MatchTestDependencies: IMatchDependencies {
    override var matchIDLoader: IMatchIDLoader = InMemoryMatchIDLoader()
    override var letterRandomizer: ILetterRandomizer = LetterRandomizer()
    override var topicLoader: ITopicLoader = InMemoryTopicLoader()
}