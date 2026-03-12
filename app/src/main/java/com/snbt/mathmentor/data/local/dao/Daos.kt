package com.snbt.mathmentor.data.local.dao

import androidx.room.*
import com.snbt.mathmentor.data.local.entity.DayEntity
import com.snbt.mathmentor.data.local.entity.ErrorLogEntity
import com.snbt.mathmentor.data.local.entity.QuizResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Query("SELECT * FROM days ORDER BY dayNumber ASC")
    fun getAllDays(): Flow<List<DayEntity>>

    @Query("SELECT * FROM days WHERE dayNumber = :dayNumber")
    suspend fun getDayByNumber(dayNumber: Int): DayEntity?

    @Query("SELECT COUNT(*) FROM days WHERE isCompleted = 1")
    fun getCompletedDaysCount(): Flow<Int>

    @Query("SELECT * FROM days WHERE isCompleted = 1 ORDER BY dayNumber DESC")
    suspend fun getCompletedDays(): List<DayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(days: List<DayEntity>)

    @Update
    suspend fun updateDay(day: DayEntity)

    @Query("SELECT AVG(score) FROM days WHERE isCompleted = 1 AND score > 0")
    fun getAverageScore(): Flow<Float?>

    @Query("SELECT COUNT(*) FROM days WHERE isCompleted = 1")
    suspend fun getCompletedDaysCountOnce(): Int
}

@Dao
interface ErrorLogDao {
    @Query("SELECT * FROM error_logs ORDER BY timestamp DESC")
    fun getAllErrorLogs(): Flow<List<ErrorLogEntity>>

    @Query("SELECT * FROM error_logs WHERE dayNumber = :dayNumber ORDER BY timestamp DESC")
    fun getErrorLogsByDay(dayNumber: Int): Flow<List<ErrorLogEntity>>

    @Insert
    suspend fun insertErrorLog(errorLog: ErrorLogEntity)

    @Delete
    suspend fun deleteErrorLog(errorLog: ErrorLogEntity)

    @Query("SELECT COUNT(*) FROM error_logs")
    fun getTotalErrorCount(): Flow<Int>
}

@Dao
interface QuizResultDao {
    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    fun getAllQuizResults(): Flow<List<QuizResultEntity>>

    @Query("SELECT * FROM quiz_results WHERE dayNumber = :dayNumber")
    suspend fun getQuizResultByDay(dayNumber: Int): QuizResultEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizResult(result: QuizResultEntity)

    @Query("SELECT SUM(totalScore) FROM quiz_results")
    fun getTotalQuestionsAnswered(): Flow<Int?>
}
