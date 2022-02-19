package com.example.dependencies

import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.IMatchIDLoader
import com.example.interfaces.ITopicLoader
import com.example.services.DBMatchIDLoader
import com.example.services.DBTopicLoader
import services.LetterRandomizer

class MatchDBDependencies: IMatchDBDependencies {
    override var matchIDLoader: IMatchIDLoader = DBMatchIDLoader()
    override var letterRandomizer: ILetterRandomizer = LetterRandomizer()
    override var topicLoader: ITopicLoader = DBTopicLoader()
}