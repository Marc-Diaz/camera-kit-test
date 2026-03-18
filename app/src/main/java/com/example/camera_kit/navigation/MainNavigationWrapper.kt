package com.example.camera_kit.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.boostar_uaal.ui.screen.arScreen.ArScreen
import com.example.camera_kit.HomeScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MainNavigationWrapper() {

    val backStack = rememberNavBackStack(Routes.Home)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.back() },
        entryProvider = entryProvider {
            entry<Routes.Home> {
                HomeScreen(
                    navigateToArScreen = { backStack.navigateTo(it )}
                )
            }

            entry<Routes.ArScreen>{
                b ->
                ArScreen(
                    back = { backStack.back() },
                    lensId = b.lensId,
                    lensGroupId = b.grouLensId,
                    onPermissionDenied = { backStack.back() }
                )
            }
        }
    )
}
