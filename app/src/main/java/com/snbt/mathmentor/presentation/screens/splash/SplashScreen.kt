package com.snbt.mathmentor.presentation.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.snbt.mathmentor.presentation.navigation.Screen
import com.snbt.mathmentor.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateNext: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scale = remember { Animatable(0.5f) }
    val isOnboardingDone by viewModel.isOnboardingDone.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f))
    }

    LaunchedEffect(isOnboardingDone) {
        if (isOnboardingDone != null) {
            delay(1500)
            onNavigateNext(if (isOnboardingDone == true) Screen.Home.route else Screen.Onboarding.route)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale.value)
        ) {
            Text("🎯", fontSize = 72.sp)
            Spacer(Modifier.height(16.dp))
            Text(
                "SNBT Math Mentor",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                "38 Hari Menuju UTBK 2026",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
            Spacer(Modifier.height(32.dp))
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
