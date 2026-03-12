package com.snbt.mathmentor.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "days")
data class DayEntity(
    @PrimaryKey val dayNumber: Int,
    val title: String,
    val isCompleted: Boolean = false,
    val score: Int = 0,
    val timeSpent: Long = 0L,
    val dateCompleted: Long = 0L
)

@Entity(tableName = "error_logs")
data class ErrorLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayNumber: Int,
    val questionId: Int,
    val userAnswer: String,
    val correctAnswer: String,
    val note: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayNumber: Int,
    val totalScore: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)
