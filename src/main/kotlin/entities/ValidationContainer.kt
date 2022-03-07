package entities

import com.example.entities.TopicAndWord

data class ValidationContainer(
    val letter: Char,
    val topicsAndWords: List<TopicAndWord>
)
