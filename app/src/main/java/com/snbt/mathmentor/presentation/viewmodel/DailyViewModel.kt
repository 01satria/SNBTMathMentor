package com.snbt.mathmentor.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snbt.mathmentor.domain.model.*
import com.snbt.mathmentor.domain.repository.SNBTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DailyUiState(
    val dayContent: DayContent? = null,
    val isLoading: Boolean = true,
    val timerSeconds: Int = 0,
    val isTimerRunning: Boolean = false,
    val quizAnswers: Map<Int, Int> = emptyMap(),
    val quizCompleted: Boolean = false,
    val quizScore: Int = 0,
    val showErrorLogDialog: Boolean = false,
    val isDayCompleted: Boolean = false
)

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val repository: SNBTRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyUiState())
    val uiState: StateFlow<DailyUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun loadDay(dayNumber: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val content = repository.getDayContent(dayNumber)
            val existingResult = repository.getQuizResult(dayNumber)
            _uiState.update {
                it.copy(
                    dayContent = content,
                    isLoading = false,
                    timerSeconds = content.studyDurationMinutes * 60,
                    quizCompleted = existingResult != null,
                    quizScore = existingResult?.totalScore ?: 0
                )
            }
        }
    }

    fun startTimer() {
        if (timerJob?.isActive == true) return
        timerJob = viewModelScope.launch {
            _uiState.update { it.copy(isTimerRunning = true) }
            while (_uiState.value.timerSeconds > 0) {
                delay(1000)
                _uiState.update { it.copy(timerSeconds = it.timerSeconds - 1) }
            }
            _uiState.update { it.copy(isTimerRunning = false) }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(isTimerRunning = false) }
    }

    fun answerQuiz(questionId: Int, answerIndex: Int) {
        _uiState.update {
            it.copy(quizAnswers = it.quizAnswers + (questionId to answerIndex))
        }
    }

    fun submitQuiz(dayNumber: Int) {
        val state = _uiState.value
        val content = state.dayContent ?: return
        var correct = 0
        content.quizQuestions.forEach { q ->
            if (state.quizAnswers[q.id] == q.correctOptionIndex) correct++
        }
        val total = content.quizQuestions.size
        viewModelScope.launch {
            repository.saveQuizResult(QuizResult(dayNumber, correct, total))
            _uiState.update {
                it.copy(
                    quizCompleted = true,
                    quizScore = correct
                )
            }
        }
    }

    fun addErrorLog(dayNumber: Int, questionId: Int, userAnswer: String, correctAnswer: String, note: String) {
        viewModelScope.launch {
            repository.addErrorLog(ErrorLog(
                dayNumber = dayNumber,
                questionId = questionId,
                userAnswer = userAnswer,
                correctAnswer = correctAnswer,
                note = note
            ))
        }
    }

    fun completeDay(dayNumber: Int) {
        val state = _uiState.value
        val timeSpent = (state.dayContent?.studyDurationMinutes?.toLong() ?: 60L) -
                (state.timerSeconds / 60L)
        viewModelScope.launch {
            repository.completeDay(dayNumber, state.quizScore, timeSpent.coerceAtLeast(1L))
            _uiState.update { it.copy(isDayCompleted = true) }
        }
    }

    fun toggleErrorLogDialog() {
        _uiState.update { it.copy(showErrorLogDialog = !it.showErrorLogDialog) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
