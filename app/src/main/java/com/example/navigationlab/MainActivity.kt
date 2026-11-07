package com.example.navigationlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.navigationlab.navigation.NavigationLabApp
import com.example.navigationlab.ui.theme.NavigationLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationLabTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavigationLabApp()
                }
            }
        }
    }
}
