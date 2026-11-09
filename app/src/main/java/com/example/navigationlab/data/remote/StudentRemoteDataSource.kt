package com.example.navigationlab.data.remote

import android.util.Log
import com.example.navigationlab.data.remote.dto.UserDto

class StudentRemoteDataSource(
    private val api: StudentApiService
) {
    suspend fun getStudents(): List<UserDto> =
        api.getUsers(limit = 10).users

    suspend fun getStudent(id: Int): UserDto =
        api.getUser(id)

    suspend fun searchStudents(name: String): List<UserDto> {
        val result = api.searchUser(name).users
        Log.i("Detail REST API", result.toString())
        return result
    }
}