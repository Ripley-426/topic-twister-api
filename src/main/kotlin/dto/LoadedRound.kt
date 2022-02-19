package com.example.dto

import com.example.interfaces.ILetterRandomizer
import com.example.interfaces.ITopicRandomizer
import com.example.interfaces.IWordValidator
import com.example.model.Round

class LoadedRound(
    roundNumber: Int,
    topicRandomizerDependency: ITopicRandomizer,
    letterRandomizerDependency: ILetterRandomizer,
    wordValidatorDependency: IWordValidator,
    Letter:Char,
    Topics:List<String>,
    PlayerAWords:List<String>,
    PlayerBWords:List<String>? = null,
    PlayerAWordsValidation:List<Boolean>,
    PlayerBWordsValidation:List<Boolean>,
    Turn: Int
) : Round(roundNumber, topicRandomizerDependency, letterRandomizerDependency, wordValidatorDependency) {
}