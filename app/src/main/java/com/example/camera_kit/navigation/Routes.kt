package com.example.camera_kit.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
sealed class Routes: NavKey {
    @Serializable
    data object Home: Routes()
    @Serializable
    data class ArScreen(val lensId: String, val grouLensId: String): Routes()
}