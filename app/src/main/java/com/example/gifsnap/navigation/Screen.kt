package com.example.gifsnap.navigation

sealed class Screen(
    val route: String
) {
    object HomeScreen : Screen("home")

    object DetailScreen : Screen("detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}