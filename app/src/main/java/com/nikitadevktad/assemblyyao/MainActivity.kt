package com.nikitadevktad.assemblyyao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.nikitadevktad.assemblyyao.ui.navigation.AppNavigation
import com.nikitadevktad.assemblyyao.ui.screens.HomeScreen
import com.nikitadevktad.assemblyyao.ui.theme.AssemblyYAOTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssemblyYAOTheme() {
                AppNavigation()
            }
        }
    }
}

