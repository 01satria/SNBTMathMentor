package com.snbt.mathmentor.data.repository

import com.snbt.mathmentor.data.local.RoadmapDataSource
import com.snbt.mathmentor.data.local.dao.DayDao
import com.snbt.mathmentor.data.local.dao.ErrorLogDao
import com.snbt.mathmentor.data.local.dao.QuizResultDao
import com.snbt.mathmentor.data.local.entity.ErrorLogEntity
import com.snbt.mathmentor.data.local.entity.QuizResultEntity
import com.snbt.mathmentor.domain.model.*
import com.snbt.mathmentor.domain.repository.SNBTRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SNBTRepositoryImpl @Inject constructor(
    private val dayDao: DayDao,
    private val errorLogDao: ErrorLogDao,
    private val quizResultDao: QuizResultDao
) : SNBTRepository {

    override fun getAllDayProgress(): Flow<List<DayProgress>> =
        dayDao.getAllDays().map { entities ->
            entities.map { e ->
                DayProgress(
                    dayNumber = e.dayNumber,
                    title = e.title,
                    isCompleted = e.isCompleted,
                    score = e.score,
                    timeSpentMinutes = e.timeSpent,
                    dateCompleted = if (e.dateCompleted > 0) e.dateCompleted else null
                )
            }
        }

    override fun getCompletedDaysCount(): Flow<Int> = dayDao.getCompletedDaysCount()

    override fun getAverageScore(): Flow<Float> = dayDao.getAverageScore().map { it ?: 0f }

    override fun getTotalQuestionsAnswered(): Flow<Int> =
        quizResultDao.getTotalQuestionsAnswered().map { it ?: 0 }

    override suspend fun getCurrentDay(): Int {
        val completed = dayDao.getCompletedDaysCountOnce()
        return (completed + 1).coerceAtMost(38)
    }

    override suspend fun getDayContent(dayNumber: Int): DayContent =
        RoadmapDataSource.getDayContent(dayNumber)

    override suspend fun completeDay(dayNumber: Int, score: Int, timeSpentMinutes: Long) {
        val day = dayDao.getDayByNumber(dayNumber) ?: return
        dayDao.updateDay(
            day.copy(
                isCompleted = true,
                score = score,
                timeSpent = timeSpentMinutes,
                dateCompleted = System.currentTimeMillis()
            )
        )
    }

    override suspend fun initializeDatabase() {
        // Hanya insert jika tabel benar-benar kosong (first launch)
        val existingCount = dayDao.getCompletedDaysCountOnce()
        val totalCount = dayDao.getTotalDaysCount()
        if (totalCount == 0) {
            dayDao.insertAll(RoadmapDataSource.getAllDayEntities())
        }
    }

    override fun getErrorLogs(): Flow<List<ErrorLog>> =
        errorLogDao.getAllErrorLogs().map { list -> list.map { it.toDomain() } }

    override fun getErrorLogsByDay(dayNumber: Int): Flow<List<ErrorLog>> =
        errorLogDao.getErrorLogsByDay(dayNumber).map { list -> list.map { it.toDomain() } }

    override suspend fun addErrorLog(errorLog: ErrorLog) {
        errorLogDao.insertErrorLog(
            ErrorLogEntity(
                dayNumber = errorLog.dayNumber,
                questionId = errorLog.questionId,
                userAnswer = errorLog.userAnswer,
                correctAnswer = errorLog.correctAnswer,
                note = errorLog.note,
                timestamp = errorLog.timestamp
            )
        )
    }

    override suspend fun saveQuizResult(result: QuizResult) {
        quizResultDao.insertQuizResult(
            QuizResultEntity(
                dayNumber = result.dayNumber,
                totalScore = result.totalScore,
                totalQuestions = result.totalQuestions,
                timestamp = result.timestamp
            )
        )
    }

    override suspend fun getQuizResult(dayNumber: Int): QuizResult? =
        quizResultDao.getQuizResultByDay(dayNumber)?.let {
            QuizResult(it.dayNumber, it.totalScore, it.totalQuestions, it.timestamp)
        }

    override suspend fun getStreak(): Int {
        val completedDays = dayDao.getCompletedDays()
        if (completedDays.isEmpty()) return 0
        var streak = 0
        val dayMs = 86_400_000L
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        var expected = today
        for (day in completedDays) {
            if (day.dateCompleted == 0L) continue
            val completedDay = (day.dateCompleted / dayMs) * dayMs
            if (completedDay >= expected - dayMs) {
                streak++
                expected -= dayMs
            } else break
        }
        return streak
    }

    override suspend fun resetProgress() {
        dayDao.resetAllDays()
    }

    private fun ErrorLogEntity.toDomain() =
        ErrorLog(id, dayNumber, questionId, userAnswer, correctAnswer, note, timestamp)
}
