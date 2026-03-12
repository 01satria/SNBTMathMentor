package com.snbt.mathmentor.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snbt.mathmentor.domain.model.HomeStats
import com.snbt.mathmentor.domain.repository.SNBTRepository
import com.snbt.mathmentor.util.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {
    val isDarkMode: StateFlow<Boolean> = userPreferences.isDarkMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}

data class HomeUiState(
    val stats: HomeStats = HomeStats(0, 38, 0, 0, 0f, 1),
    val isLoading: Boolean = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SNBTRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    val isOnboardingDone: StateFlow<Boolean> = userPreferences.isOnboardingDone
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    init {
        initializeAndLoad()
    }

    private fun initializeAndLoad() {
        viewModelScope.launch {
            val initialized = userPreferences.isDbInitialized.first()
            if (!initialized) {
                repository.initializeDatabase()
                userPreferences.setDbInitialized()
            }
            loadStats()
        }
    }

    fun setOnboardingDone() {
        viewModelScope.launch {
            userPreferences.setOnboardingDone()
        }
    }

    private fun loadStats() {
        combine(
            repository.getCompletedDaysCount(),
            repository.getAverageScore(),
            repository.getTotalQuestionsAnswered()
        ) { completed, avg, total -> Triple(completed, avg, total) }
            .onEach { (completed, avg, total) ->
                val currentDay = repository.getCurrentDay()
                val streak = repository.getStreak()
                _uiState.update {
                    it.copy(
                        stats = HomeStats(completed, 38, streak, total, avg, currentDay),
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
