package com.snbt.mathmentor.di

import android.content.Context
import androidx.room.Room
import com.snbt.mathmentor.data.local.dao.DayDao
import com.snbt.mathmentor.data.local.dao.ErrorLogDao
import com.snbt.mathmentor.data.local.dao.QuizResultDao
import com.snbt.mathmentor.data.local.database.SNBTDatabase
import com.snbt.mathmentor.data.repository.SNBTRepositoryImpl
import com.snbt.mathmentor.domain.repository.SNBTRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SNBTDatabase =
        Room.databaseBuilder(context, SNBTDatabase::class.java, "snbt_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDayDao(db: SNBTDatabase): DayDao = db.dayDao()

    @Provides
    fun provideErrorLogDao(db: SNBTDatabase): ErrorLogDao = db.errorLogDao()

    @Provides
    fun provideQuizResultDao(db: SNBTDatabase): QuizResultDao = db.quizResultDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSNBTRepository(impl: SNBTRepositoryImpl): SNBTRepository
}
