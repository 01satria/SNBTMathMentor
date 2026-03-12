package com.snbt.mathmentor.presentation.screens.progress

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.snbt.mathmentor.domain.model.DayProgress
import com.snbt.mathmentor.domain.repository.SNBTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(repo: SNBTRepository) : ViewModel() {
    val days: StateFlow<List<DayProgress>> = repo.getAllDayProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    onBack: () -> Unit,
    viewModel: ProgressViewModel = hiltViewModel()
) {
    val days by viewModel.days.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📊 Progress 38 Hari", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Kembali") } }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(padding)
        ) {
            items(days, key = { it.dayNumber }) { day ->
                DayProgressItem(day)
            }
        }
    }
}

@Composable
private fun DayProgressItem(day: DayProgress) {
    val completed = day.isCompleted
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (completed) MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            Modifier.padding(14.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                if (completed) "✅" else "⬜",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Hari ${day.dayNumber}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                Text(day.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                if (completed && day.score > 0) {
                    Text("Skor: ${day.score} • ${day.timeSpentMinutes} menit", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
                }
            }
            if (completed) {
                Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.secondary)
            } else {
                Icon(Icons.Default.Lock, null, tint = MaterialTheme.colorScheme.onSurface.copy(0.3f))
            }
        }
    }
}
