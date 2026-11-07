package com.example.navigationlab.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationlab.Routes
import com.example.navigationlab.data.remote.RetrofitClient
import com.example.navigationlab.data.remote.StudentRemoteDataSource
import com.example.navigationlab.ui.screen.AboutScreen
import com.example.navigationlab.ui.screen.DetailScreen
import com.example.navigationlab.ui.screen.HomeScreen
import com.example.navigationlab.ui.screen.ProfileScreen
import com.example.navigationlab.data.source.StudentLocalDataSource
import com.example.navigationlab.data.repository.DefaultStudentRepository
import com.example.navigationlab.ui.detail.DetailViewModel
import com.example.navigationlab.ui.home.HomeViewModel
import okhttp3.Route

@Composable
fun NavigationLabApp() {
    val remoteDataSource = remember {
        StudentRemoteDataSource(RetrofitClient.apiService)
    }
    val repository = remember {
        DefaultStudentRepository(remoteDataSource)
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) {
            val homeViewModel = remember { HomeViewModel(repository) }
            HomeScreen(
                uiState = homeViewModel.uiState,
                onStudentClick = { id -> navController.navigate(Routes.detail(id)) },
                onRetry = homeViewModel::loadStudents,
                onSearch = homeViewModel::searchStudent,
                onOpenProfile = {
                    navController.navigate(Routes.PROFILE)
                },
                onOpenAbout = {
                    navController.navigate(Routes.ABOUT)
                }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(
                navArgument("studentId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val studentId =
                backStackEntry.arguments?.getInt("studentId") ?: -1

            val viewModel: DetailViewModel = viewModel(
                factory = DetailViewModel.factory(repository)
            )

            LaunchedEffect(studentId) {
                viewModel.loadStudent(studentId)
            }

            DetailScreen(
                uiState = viewModel.uiState,
                onBack = { navController.popBackStack() }

            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.ABOUT) {
            AboutScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}