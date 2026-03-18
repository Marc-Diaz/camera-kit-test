package com.example.camera_kit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.camera_kit.navigation.MainNavigationWrapper
import com.example.camera_kit.navigation.Routes
import com.example.camera_kit.ui.theme.CamerakitTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CamerakitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainNavigationWrapper()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navigateToArScreen: (Routes) -> Unit) {
    Column(modifier = Modifier.padding(40.dp)){
        Button(
            onClick = { navigateToArScreen(Routes.ArScreen(
                lensId = "",
                grouLensId = ""
            ) ) },
            content = { Text("Pantalla AR") }
        )
    }
}

