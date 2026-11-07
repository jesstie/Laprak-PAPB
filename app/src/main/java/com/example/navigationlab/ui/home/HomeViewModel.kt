package com.example.navigationlab.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigationlab.data.repository.StudentRepository

class HomeViewModel(
    private val repository: StudentRepository
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set
    init { loadStudents() }
    fun loadStudents() {
        uiState = uiState.copy(isLoading = true, errorMessage = null)
        repository.getStudents { result ->
            result
                .onSuccess { data ->
                    uiState = HomeUiState(students = data)
                }
                .onFailure { error ->
                    uiState = HomeUiState(
                        errorMessage = error.message ?: "Gagal mengambil data"
                    )
                }
        }
    }

    fun searchStudent(name: String) {
        val query = name.trim()
        if (query.isEmpty()) {
            loadStudents()
            return
        }
        uiState = uiState.copy(isLoading = true, errorMessage = null)
        repository.searchStudent(query) { result ->
            result
                .onSuccess { data ->
                    uiState = HomeUiState(students = data, searchQuery = query)
                }
                .onFailure { error ->
                    uiState = HomeUiState(
                        errorMessage = error.message ?: "Gagal mengambil data"
                    )
                }
        }
    }
}