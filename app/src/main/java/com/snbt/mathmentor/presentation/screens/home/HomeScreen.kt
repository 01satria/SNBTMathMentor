package com.snbt.mathmentor.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snbt.mathmentor.domain.model.HomeStats
import com.snbt.mathmentor.presentation.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onStartDay: (Int) -> Unit,
    onNavigateProgress: () -> Unit,
    onNavigateErrorLog: () -> Unit,
    onNavigateSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🎯 SNBT Math Mentor", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateSettings) {
                        Icon(Icons.Default.Settings, "Pengaturan")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Beranda") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Timeline, null) },
                    label = { Text("Progress") },
                    selected = false,
                    onClick = onNavigateProgress
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ErrorOutline, null) },
                    label = { Text("Error Log") },
                    selected = false,
                    onClick = onNavigateErrorLog
                )
            }
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            HomeContent(
                stats = uiState.stats,
                onStartDay = onStartDay,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun HomeContent(
    stats: HomeStats,
    onStartDay: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Progress Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(Modifier.padding(20.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📈 Progress Belajar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        "${stats.completedDays}/38 Hari",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { stats.completedDays / 38f },
                    modifier = Modifier.fillMaxWidth().height(12.dp),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${(stats.completedDays / 38f * 100).toInt()}% selesai",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(0.7f)
                )
            }
        }

        // Quick Stats Row
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("🔥 Streak", "${stats.currentStreak} hari", Modifier.weight(1f))
            StatCard("📊 Rata-rata", "${stats.averageScore.toInt()}%", Modifier.weight(1f))
            StatCard("✍️ Total Soal", "${stats.totalQuestionsAnswered}", Modifier.weight(1f))
        }

        // Today's Main Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(Modifier.padding(24.dp)) {
                Text(
                    "Hari Ini",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Hari ${stats.currentDay} dari 38",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    if (stats.completedDays < 38)
                        "💪 Kamu sudah hampir sampai! Jangan berhenti sekarang."
                    else
                        "🎉 Selamat! Kamu telah menyelesaikan semua 38 hari!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                )
                Spacer(Modifier.height(20.dp))
                if (stats.completedDays < 38) {
                    Button(
                        onClick = { onStartDay(stats.currentDay) },
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Mulai Belajar Hari ${stats.currentDay}",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = { onStartDay(38) },
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        Text("🔁 Ulangi Hari 38", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }

        // Motivation
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("💡", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.width(12.dp))
                Text(
                    "\"Konsistensi 38 hari > belajar panik semalam. Kamu bisa!\"",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
        }
    }
}
