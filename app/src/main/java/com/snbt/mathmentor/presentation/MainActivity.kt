package com.snbt.mathmentor.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.snbt.mathmentor.presentation.navigation.SNBTNavHost
import com.snbt.mathmentor.presentation.navigation.Screen
import com.snbt.mathmentor.presentation.theme.SNBTMathMentorTheme
import com.snbt.mathmentor.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

            SNBTMathMentorTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                SNBTNavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route
                )
            }
        }
    }
}
