package com.snbt.mathmentor.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.snbt.mathmentor.presentation.screens.daily.DailyLearningScreen
import com.snbt.mathmentor.presentation.screens.errorlog.ErrorLogScreen
import com.snbt.mathmentor.presentation.screens.home.HomeScreen
import com.snbt.mathmentor.presentation.screens.onboarding.OnboardingScreen
import com.snbt.mathmentor.presentation.screens.progress.ProgressScreen
import com.snbt.mathmentor.presentation.screens.settings.SettingsScreen
import com.snbt.mathmentor.presentation.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Daily : Screen("daily/{dayNumber}") {
        fun createRoute(dayNumber: Int) = "daily/$dayNumber"
    }
    object Progress : Screen("progress")
    object ErrorLog : Screen("error_log")
    object Settings : Screen("settings")
}

@Composable
fun SNBTNavHost(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateNext = { destination ->
                    navController.navigate(destination) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onDone = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onStartDay = { dayNumber ->
                    navController.navigate(Screen.Daily.createRoute(dayNumber))
                },
                onNavigateProgress = { navController.navigate(Screen.Progress.route) },
                onNavigateErrorLog = { navController.navigate(Screen.ErrorLog.route) },
                onNavigateSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(
            route = Screen.Daily.route,
            arguments = listOf(navArgument("dayNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayNumber = backStackEntry.arguments?.getInt("dayNumber") ?: 1
            DailyLearningScreen(
                dayNumber = dayNumber,
                onBack = { navController.popBackStack() },
                onDayCompleted = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Progress.route) {
            ProgressScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.ErrorLog.route) {
            ErrorLogScreen(onBack = { navController.popBackStack() })
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
