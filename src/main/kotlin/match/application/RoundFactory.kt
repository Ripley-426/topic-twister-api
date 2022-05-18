package com.example.match.application

import com.example.letterRandomizer.ILetterRandomizer
import com.example.match.domain.IRoundFactory
import com.example.match.domain.Round
import com.example.topic.domain.ITopicRandomizer
import com.example.wordValidator.domain.IValidateWords

class RoundFactory(
    val topicRandomizer: ITopicRandomizer,
    val letterRandomizer: ILetterRandomizer,
    val wordsValidator: IValidateWords
): IRoundFactory {

    private val numberOfTopics = 5

    override fun getRounds(numberOfRounds: Int): MutableList<Round> {
        var rounds: MutableList<Round> = mutableListOf()
        for (i in 1..numberOfRounds){
            var round = Round(i, wordsValidator)
            round.letter = letterRandomizer.getRandomLetter()
            round.topics = topicRandomizer.getRandomTopics(numberOfTopics)
            rounds.add(round)
        }
        return rounds
    }
}