package com.snbt.mathmentor.domain.model

data class DayContent(
    val dayNumber: Int,
    val title: String,
    val objectives: List<String>,
    val materialSummary: String,
    val keyFormulas: List<String>,
    val youtubeVideoId: String,
    val youtubeTitle: String,
    val exercises: List<Exercise>,
    val quizQuestions: List<QuizQuestion>,
    val studyDurationMinutes: Int = 60
)

data class Exercise(
    val id: Int,
    val question: String,
    val difficulty: Difficulty,
    val answer: String,
    val explanation: String
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

enum class Difficulty {
    MUDAH, SEDANG, SULIT
}

data class DayProgress(
    val dayNumber: Int,
    val title: String,
    val isCompleted: Boolean,
    val score: Int,
    val timeSpentMinutes: Long,
    val dateCompleted: Long?
)

data class ErrorLog(
    val id: Int = 0,
    val dayNumber: Int,
    val questionId: Int,
    val userAnswer: String,
    val correctAnswer: String,
    val note: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class QuizResult(
    val dayNumber: Int,
    val totalScore: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)

data class HomeStats(
    val completedDays: Int,
    val totalDays: Int = 38,
    val currentStreak: Int,
    val totalQuestionsAnswered: Int,
    val averageScore: Float,
    val currentDay: Int
)
