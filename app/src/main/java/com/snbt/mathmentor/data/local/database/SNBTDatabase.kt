package com.snbt.mathmentor.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snbt.mathmentor.data.local.dao.DayDao
import com.snbt.mathmentor.data.local.dao.ErrorLogDao
import com.snbt.mathmentor.data.local.dao.QuizResultDao
import com.snbt.mathmentor.data.local.entity.DayEntity
import com.snbt.mathmentor.data.local.entity.ErrorLogEntity
import com.snbt.mathmentor.data.local.entity.QuizResultEntity

@Database(
    entities = [DayEntity::class, ErrorLogEntity::class, QuizResultEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SNBTDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun errorLogDao(): ErrorLogDao
    abstract fun quizResultDao(): QuizResultDao
}
