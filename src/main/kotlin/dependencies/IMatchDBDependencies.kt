package com.example.dependencies

import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.IMatchIDLoader
import com.example.interfaces.ITopicLoader

interface IMatchDBDependencies {
    var matchIDLoader: IMatchIDLoader
    var letterRandomizer: ILetterRandomizer
    var topicLoader: ITopicLoader
}