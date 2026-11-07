package com.example.navigationlab.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navigationlab.ui.detail.DetailUiState

@Composable
fun DetailScreen(
    uiState: DetailUiState,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Detail Student", style = MaterialTheme.typography.headlineMedium)
        when {
            uiState.errorMessage != null ->
                Text(uiState.errorMessage)
            uiState.student != null -> {
                val student = uiState.student
                student?.let { data ->
                    Text(data.name, style = MaterialTheme.typography.headlineSmall)
                    Text("Email: ${data.email}")
                    Text("University: ${data.university}")
                }
            }
        }
        Button(onClick = onBack) {
            Text("Kembali")
        }
    }
}