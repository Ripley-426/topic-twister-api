package com.example.match.domain

import com.example.topic.domain.ITopicLoader
import com.example.letterRandomizer.ILetterRandomizer

interface IMatchDependencies {
    var matchIDLoader: IMatchIDLoader
    var letterRandomizer: ILetterRandomizer
    var topicLoader: ITopicLoader
}