package com.example.dto

import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.ITopicRandomizer
import com.example.interfaces.IWordValidator
import com.example.model.Round

class LoadedRound(
    roundNumber: Int,
    topicRandomizerDependency: ITopicRandomizer,
    letterRandomizerDependency: ILetterRandomizer,
    wordValidatorDependency: IWordValidator
) : Round(roundNumber, topicRandomizerDependency, letterRandomizerDependency, wordValidatorDependency) {
}