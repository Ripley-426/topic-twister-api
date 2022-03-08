package com.example.match.infrastructure

import com.example.topic.infrastructure.DBTopicLoader
import com.example.letterRandomizer.ILetterRandomizer
import com.example.match.domain.IMatchIDLoader
import com.example.topic.domain.ITopicLoader
import com.example.match.domain.IMatchDependencies
import com.example.letterRandomizer.LetterRandomizer

class MatchDBDependencies: IMatchDependencies {
    override var matchIDLoader: IMatchIDLoader = DBMatchIDLoader()
    override var letterRandomizer: ILetterRandomizer = LetterRandomizer()
    override var topicLoader: ITopicLoader = DBTopicLoader()
}