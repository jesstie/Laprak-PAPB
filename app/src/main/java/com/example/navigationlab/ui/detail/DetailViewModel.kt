package com.example.navigationlab.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigationlab.data.repository.StudentRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel( private val repository: StudentRepository ) : ViewModel() {
    private val _uistate = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uistate.asStateFlow()
    fun loadStudent(id: Int) {
        viewModelScope.launch {
            _uistate.value = DetailUiState(isLoading = true)

            try {
                val student = repository.getStudent(id)
                _uistate.value = DetailUiState(student = student)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uistate.value = DetailUiState(
                    errorMessage = e.message ?: "Data tidak tersedia"
                )
            }
        }
    }
    companion object {
        fun DetailViewModelFactory(repository: StudentRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DetailViewModel(repository) as T
                }
            }
    }
}