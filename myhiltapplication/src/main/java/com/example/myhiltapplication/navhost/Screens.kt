package com.example.myhiltapplication.navhost

enum class Screen {
    FIRST, SECOND, Third
}

sealed class NavigationItem(val route: String) {
    object First: NavigationItem(Screen.FIRST.name)
    object Second: NavigationItem(Screen.SECOND.name)
    object Third: NavigationItem(Screen.Third.name)
}