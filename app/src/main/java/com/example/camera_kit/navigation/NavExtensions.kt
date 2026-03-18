package com.example.camera_kit.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(screen: NavKey, inclusive: Boolean = false) {
    if (inclusive)
        while (isNotEmpty())
            removeLastOrNull()
    add(screen)
}

fun NavBackStack<NavKey>.back() {
    if (isEmpty()) return
    removeLastOrNull()
}

fun NavBackStack<NavKey>.backTo(targetScreen: NavKey) {
    if (isEmpty()) return
    if(targetScreen !in this) return

    while(isNotEmpty() && last() != targetScreen){
        removeLastOrNull()
    }

}