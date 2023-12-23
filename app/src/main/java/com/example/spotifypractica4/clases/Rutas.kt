package com.example.spotifypractica4.clases

sealed class Rutas (val ruta : String) {
    object PantallaReproductor: Rutas("pantallaReproductor")

}
