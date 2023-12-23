package com.example.spotifypractica4.navegacion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spotifypractica4.clases.Rutas
import com.example.spotifypractica4.reproductor.PantallaReproductor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrafoDeNavegacion() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rutas.PantallaReproductor.ruta) {
        composable(Rutas.PantallaReproductor.ruta) {

            PantallaReproductor(navController =  navController)
        }
    }

}