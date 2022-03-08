package com.example.dependencies

import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.IMatchIDLoader
import com.example.interfaces.ITopicLoader
import com.example.tempPermanence.InMemoryMatchIDLoader
import com.example.tempPermanence.InMemoryTopicLoader
import services.LetterRandomizer

class TestDependencies: IMatchDBDependencies {
    override var matchIDLoader: IMatchIDLoader = InMemoryMatchIDLoader()
    override var letterRandomizer: ILetterRandomizer = LetterRandomizer()
    override var topicLoader: ITopicLoader = InMemoryTopicLoader()
}