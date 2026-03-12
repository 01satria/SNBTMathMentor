package com.snbt.mathmentor.presentation.screens.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snbt.mathmentor.domain.model.Difficulty
import com.snbt.mathmentor.domain.model.Exercise
import com.snbt.mathmentor.domain.model.QuizQuestion
import com.snbt.mathmentor.presentation.viewmodel.DailyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLearningScreen(
    dayNumber: Int,
    onBack: () -> Unit,
    onDayCompleted: () -> Unit,
    viewModel: DailyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(dayNumber) { viewModel.loadDay(dayNumber) }
    LaunchedEffect(uiState.isDayCompleted) {
        if (uiState.isDayCompleted) onDayCompleted()
    }

    if (uiState.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val content = uiState.dayContent ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Hari $dayNumber", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                        Text(content.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Kembali") }
                },
                actions = {
                    // Timer chip
                    val minutes = uiState.timerSeconds / 60
                    val seconds = uiState.timerSeconds % 60
                    FilterChip(
                        selected = uiState.isTimerRunning,
                        onClick = { if (uiState.isTimerRunning) viewModel.pauseTimer() else viewModel.startTimer() },
                        label = { Text("%02d:%02d".format(minutes, seconds)) },
                        leadingIcon = {
                            Icon(
                                if (uiState.isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                null, Modifier.size(16.dp)
                            )
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            // Objectives
            item {
                SectionCard(title = "🎯 Tujuan Belajar Hari Ini") {
                    content.objectives.forEach { obj ->
                        Row(Modifier.padding(vertical = 2.dp)) {
                            Text("✓ ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text(obj, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // Material Summary
            item {
                SectionCard(title = "📚 Ringkasan Materi & Rumus Kunci") {
                    Text(content.materialSummary, style = MaterialTheme.typography.bodyMedium)
                    if (content.keyFormulas.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Text("🔑 Rumus Kunci:", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        content.keyFormulas.forEach { formula ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                            ) {
                                Text(formula, Modifier.padding(8.dp), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            // YouTube Video
            item {
                val uriHandler = LocalUriHandler.current
                SectionCard(title = "🎬 Rekomendasi Video") {
                    OutlinedCard(
                        onClick = { uriHandler.openUri("https://www.youtube.com/watch?v=${content.youtubeVideoId}") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier.size(64.dp, 48.dp).background(Color(0xFFFF0000), MaterialTheme.shapes.small),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.PlayArrow, null, tint = Color.White, modifier = Modifier.size(32.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(content.youtubeTitle, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                Text("Tap untuk buka YouTube ▶", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }

            // Exercises
            item {
                Text("✍️ Latihan Soal (${content.exercises.size} soal)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            items(content.exercises, key = { it.id }) { exercise ->
                ExerciseItem(
                    exercise = exercise,
                    onLogError = { viewModel.toggleErrorLogDialog() }
                )
            }

            // Quiz
            item {
                Spacer(Modifier.height(8.dp))
                Text("🧠 Quiz Mini", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                if (uiState.quizCompleted) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("✅", style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text("Quiz Selesai!", fontWeight = FontWeight.Bold)
                                Text("Skor: ${uiState.quizScore}/${content.quizQuestions.size} (${(uiState.quizScore * 100 / content.quizQuestions.size)}%)")
                            }
                        }
                    }
                }
            }
            if (!uiState.quizCompleted) {
                items(content.quizQuestions, key = { "quiz_${it.id}" }) { question ->
                    QuizItem(
                        question = question,
                        selectedAnswer = uiState.quizAnswers[question.id],
                        onAnswer = { viewModel.answerQuiz(question.id, it) }
                    )
                }
                item {
                    val allAnswered = content.quizQuestions.all { uiState.quizAnswers.containsKey(it.id) }
                    Button(
                        onClick = { viewModel.submitQuiz(dayNumber) },
                        enabled = allAnswered,
                        modifier = Modifier.fillMaxWidth().height(52.dp)
                    ) {
                        Text("Submit Quiz ✅")
                    }
                }
            }

            // Error Log + Complete Buttons
            item {
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { viewModel.toggleErrorLogDialog() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.ErrorOutline, null)
                    Spacer(Modifier.width(8.dp))
                    Text("📝 Catat Error Log")
                }
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.completeDay(dayNumber) },
                    enabled = uiState.quizCompleted,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(Icons.Default.CheckCircle, null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (uiState.quizCompleted) "🎉 Selesai Hari $dayNumber!" else "Selesaikan Quiz Dulu 🔒",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }

    // Error Log Dialog
    if (uiState.showErrorLogDialog) {
        ErrorLogDialog(
            dayNumber = dayNumber,
            onDismiss = { viewModel.toggleErrorLogDialog() },
            onSave = { qId, userAns, correctAns, note ->
                viewModel.addErrorLog(dayNumber, qId, userAns, correctAns, note)
                viewModel.toggleErrorLogDialog()
            }
        )
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun ExerciseItem(exercise: Exercise, onLogError: () -> Unit) {
    val difficultyColor = when (exercise.difficulty) {
        Difficulty.MUDAH -> Color(0xFF4CAF50)
        Difficulty.SEDANG -> Color(0xFFFF9800)
        Difficulty.SULIT -> Color(0xFFF44336)
    }
    var showAnswer by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(color = difficultyColor, shape = MaterialTheme.shapes.small) {
                    Text(
                        exercise.difficulty.name,
                        Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text("Soal ${exercise.id}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface.copy(0.6f))
            }
            Spacer(Modifier.height(6.dp))
            Text(exercise.question, style = MaterialTheme.typography.bodyMedium)
            if (showAnswer) {
                Spacer(Modifier.height(8.dp))
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
                    Column(Modifier.padding(10.dp)) {
                        Text("Jawaban: ${exercise.answer}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                        Text(exercise.explanation, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { showAnswer = !showAnswer }) {
                    Text(if (showAnswer) "Sembunyikan" else "Lihat Jawaban")
                }
            }
        }
    }
}

@Composable
private fun QuizItem(question: QuizQuestion, selectedAnswer: Int?, onAnswer: (Int) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Q${question.id}: ${question.question}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(8.dp))
            question.options.forEachIndexed { index, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(
                        selected = selectedAnswer == index,
                        onClick = { onAnswer(index) }
                    )
                    Text(option, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun ErrorLogDialog(
    dayNumber: Int,
    onDismiss: () -> Unit,
    onSave: (Int, String, String, String) -> Unit
) {
    var questionId by remember { mutableStateOf("") }
    var userAnswer by remember { mutableStateOf("") }
    var correctAnswer by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("📝 Catat Error Log") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = questionId, onValueChange = { questionId = it }, label = { Text("Nomor Soal") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = userAnswer, onValueChange = { userAnswer = it }, label = { Text("Jawaban Saya") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = correctAnswer, onValueChange = { correctAnswer = it }, label = { Text("Jawaban Benar") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Catatan") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(questionId.toIntOrNull() ?: 0, userAnswer, correctAnswer, note)
            }) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}
