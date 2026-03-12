package com.snbt.mathmentor.presentation.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snbt.mathmentor.presentation.viewmodel.HomeViewModel

@Composable
fun OnboardingScreen(
    onDone: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))
        Text("🎓", style = MaterialTheme.typography.displayLarge)
        Spacer(Modifier.height(16.dp))
        Text(
            "Selamat Datang di\nSNBT Math Mentor!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Program intensif 38 hari untuk menaklukkan Matematika UTBK 2026",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(32.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    "📜 Aturan Program 38 Hari",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                RuleItem("📅", "Belajar setiap hari tanpa skip — konsistensi adalah kunci!")
                RuleItem("🔒", "Hari berikutnya hanya terbuka setelah hari ini selesai")
                RuleItem("✅", "Kerjakan quiz dulu sebelum tandai hari selesai")
                RuleItem("📝", "Catat setiap kesalahan di Error Log untuk review")
                RuleItem("⏱️", "Gunakan timer untuk disiplin waktu belajar")
                RuleItem("🎯", "Target: skor quiz minimal 70% setiap hari")
            }
        }

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    "🗓️ Roadmap 38 Hari",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))
                PhaseItem("Fase 1 (Hari 1-8)", "Aritmetika & Aljabar Dasar")
                PhaseItem("Fase 2 (Hari 9-12)", "Geometri Dasar")
                PhaseItem("Fase 3 (Hari 13-22)", "Aljabar & Geometri Lanjutan")
                PhaseItem("Fase 4 (Hari 23-28)", "Statistika & Peluang")
                PhaseItem("Fase 5 (Hari 29-35)", "Penalaran Matematika")
                PhaseItem("Fase 6 (Hari 36-38)", "Tryout & Final Review")
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                // Simpan status onboarding selesai, lalu navigasi
                viewModel.setOnboardingDone()
                onDone()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("🚀 Aku Siap Belajar! Mulai Hari 1", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun RuleItem(emoji: String, text: String) {
    Row(Modifier.padding(vertical = 4.dp)) {
        Text(emoji)
        Spacer(Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun PhaseItem(phase: String, desc: String) {
    Row(Modifier.padding(vertical = 3.dp)) {
        Text("• ", color = MaterialTheme.colorScheme.primary)
        Column {
            Text(phase, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            Text(
                desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
            )
        }
    }
}
