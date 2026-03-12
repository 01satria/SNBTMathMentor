package com.snbt.mathmentor.presentation.screens.errorlog

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
import com.snbt.mathmentor.domain.model.ErrorLog
import com.snbt.mathmentor.domain.repository.SNBTRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import java.text.SimpleDateFormat
import java.util.*

@HiltViewModel
class ErrorLogViewModel @Inject constructor(repo: SNBTRepository) : ViewModel() {
    val errorLogs: StateFlow<List<ErrorLog>> = repo.getErrorLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorLogScreen(
    onBack: () -> Unit,
    viewModel: ErrorLogViewModel = hiltViewModel()
) {
    val logs by viewModel.errorLogs.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📝 Error Log", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Kembali") } }
            )
        }
    ) { padding ->
        if (logs.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎉", style = MaterialTheme.typography.displayMedium)
                    Spacer(Modifier.height(8.dp))
                    Text("Belum ada catatan error!", style = MaterialTheme.typography.titleMedium)
                    Text("Terus pertahankan!", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(padding)
            ) {
                item {
                    Text("Total: ${logs.size} error tercatat", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
                }
                items(logs, key = { it.id }) { log -> ErrorLogItem(log) }
            }
        }
    }
}

@Composable
private fun ErrorLogItem(log: ErrorLog) {
    val dateStr = remember(log.timestamp) {
        SimpleDateFormat("dd MMM yyyy HH:mm", Locale("id")).format(Date(log.timestamp))
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(0.3f))
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Hari ${log.dayNumber} • Soal ${log.questionId}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text(dateStr, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(0.5f))
            }
            Spacer(Modifier.height(6.dp))
            Row {
                Text("Jawabku: ", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                Text(log.userAnswer, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
            }
            Row {
                Text("Benar: ", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                Text(log.correctAnswer, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
            if (log.note.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text("📌 ${log.note}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(0.7f))
            }
        }
    }
}
