package com.snbt.mathmentor.domain.repository

import com.snbt.mathmentor.domain.model.*
import kotlinx.coroutines.flow.Flow

interface SNBTRepository {
    fun getAllDayProgress(): Flow<List<DayProgress>>
    fun getCompletedDaysCount(): Flow<Int>
    fun getAverageScore(): Flow<Float>
    fun getTotalQuestionsAnswered(): Flow<Int>
    suspend fun getCurrentDay(): Int
    suspend fun getDayContent(dayNumber: Int): DayContent
    suspend fun completeDay(dayNumber: Int, score: Int, timeSpentMinutes: Long)
    suspend fun initializeDatabase()
    fun getErrorLogs(): Flow<List<ErrorLog>>
    fun getErrorLogsByDay(dayNumber: Int): Flow<List<ErrorLog>>
    suspend fun addErrorLog(errorLog: ErrorLog)
    suspend fun saveQuizResult(result: QuizResult)
    suspend fun getQuizResult(dayNumber: Int): QuizResult?
    suspend fun getStreak(): Int
    suspend fun resetProgress()
}
