package com.aitc.expensetrackerandroid.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
fun NavController.navigateSingleTop(route: Route) {
    navigate(route.path) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateTopLevel(route: Route) {
    navigate(route.path) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToExpenseDetail(expenseId: Long) {
    navigateSingleTop(Route.ExpenseDetail(expenseId))
}
