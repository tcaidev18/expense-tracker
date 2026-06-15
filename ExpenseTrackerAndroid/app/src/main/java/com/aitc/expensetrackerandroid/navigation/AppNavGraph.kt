package com.aitc.expensetrackerandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aitc.expensetrackerandroid.navigation.Route.ExpenseDetail
import com.aitc.expensetrackerandroid.navigation.Route.ExpenseList
import com.aitc.expensetrackerandroid.navigation.Route.Splash
import com.aitc.expensetrackerandroid.ui.screens.expensedetail.ExpenseDetailScreen
import com.aitc.expensetrackerandroid.ui.screens.expenselist.ExpenseListScreen
import com.aitc.expensetrackerandroid.ui.screens.splash.SplashScreen

import com.aitc.expensetrackerandroid.navigation.Route.Welcome
import com.aitc.expensetrackerandroid.ui.screens.welcome.WelcomeScreen

import com.aitc.expensetrackerandroid.navigation.Route.Debug
import com.aitc.expensetrackerandroid.ui.screens.debug.DebugScreen

// SCAFFOLD:IMPORTS

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Splash.path,
    ) {
        composable(ExpenseList.path) {
            ExpenseListScreen(
                onOpenExpenseDetail = { expenseId ->
                    navController.navigateToExpenseDetail(expenseId)
                },
            )
        }
        composable(
            route = ExpenseDetail.path,
            arguments = listOf(
                navArgument(ExpenseDetail.ARG_EXPENSE_ID) {
                    type = NavType.LongType
                },
            ),
        ) {
            ExpenseDetailScreen()
        }
        composable(Splash.path) {
            SplashScreen(
                onNavigateToMain = {
                    navController.navigate(ExpenseList.path) {
                        popUpTo(Splash.path) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToWelcome = {
                    navController.navigate(Welcome.path) {
                        popUpTo(Splash.path) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Welcome.path) {
            WelcomeScreen()
        }

        composable(Debug.path) {
            DebugScreen(onBack = { navController.popBackStack() })
        }

        // SCAFFOLD:DESTINATIONS
    }
}
