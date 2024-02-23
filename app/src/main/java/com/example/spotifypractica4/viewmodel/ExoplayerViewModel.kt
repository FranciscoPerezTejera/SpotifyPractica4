package com.example.spotifypractica4.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import com.example.spotifypractica4.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

data class Cancion(val nombre: String, val album: String, val caratula: Int, val cancion: Int)

class ExoPlayerViewModel : ViewModel() {

    private val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    private val listaDeCanciones = listOf(
        Cancion(
            "Arcangel ft. BadBunny - Tú no vive así",
            "Tú no vive así",
            R.drawable.tunoviveasi,
            R.raw.arcangelbadbunnytunovivesasi
        ),
        Cancion("Bad Bunny - Amorfoda",
            "Amorfoda",
            R.drawable.amorfoda,
            R.raw.badbunnyamorfoda),
        Cancion(
            "Bad Bunny - Booker T",
            "El último tour del mundo",
            R.drawable.bookert,
            R.raw.badbunnybookert
        ),
        Cancion(
            "Bad Bunny ft. Jhay Cortez - Dákiti",
            "El último tour del mundo",
            R.drawable.dakiti,
            R.raw.badbunnyxjhaycortezdakiti
        ),
        Cancion(
            "Bad Bunny - Baticano",
            "Nadie sabe lo que va a pasar mañana",
            R.drawable.baticano,
            R.raw.badbunnybaticano
        )
    )

    private val _cancionActual = MutableStateFlow(listaDeCanciones[0])
    val cancionActual = _cancionActual.asStateFlow()

    private val _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    private var indiceCancionActual = 0

    private val _repetirCancion = MutableStateFlow(false)
    val repetirCancion = _repetirCancion.asStateFlow()

    private val _cancionRandom = MutableStateFlow(false)
    val cancionRandom = _cancionRandom.asStateFlow()

    fun crearExoPlayer(context: Context) {

        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true

    }

    fun empezarMusica(context: Context) {

        val mediaItem = MediaItem.fromUri(obtenerRuta(context, _cancionActual.value.cancion))
        _exoPlayer.value!!.setMediaItem(mediaItem)
        _exoPlayer.value!!.playWhenReady = true
        _exoPlayer.value!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    _duracion.value = _exoPlayer.value?.duration?.toInt() ?: 0

                    viewModelScope.launch {
                        while (isActive) {
                            _progreso.value = _exoPlayer.value?.currentPosition?.toInt() ?: 0
                            delay(1000)
                        }
                    }
                } else if (playbackState == Player.STATE_ENDED) {
                    // El Player ha terminado de reproducir el archivo.
                    cambiarCancion(context)
                }
            }
        }
        )
    }

    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun pausarReanudarMusica() {
        if (_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.pause()
        } else {
            _exoPlayer.value!!.play()
        }
    }

    fun cambiarCancion(context: Context) {

        _exoPlayer.value?.stop()
        _exoPlayer.value?.clearMediaItems()

        if (repetirCancion.value) {
            _exoPlayer.value?.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _cancionActual.value.cancion)))
            _exoPlayer.value?.prepare()
            _exoPlayer.value?.playWhenReady = true
        } else {
            siguienteCancion(context)
        }
    }

    private fun siguienteCancion(context: Context) {
        if (_cancionRandom.value) {
            CancionRandom(context)
        } else {
            SiguienteCancionLista(context)
        }
    }

    private fun CancionRandom(context: Context) {
        var cancionAleatoria: Int
        do {
            cancionAleatoria = (listaDeCanciones.indices).random()
        } while (cancionAleatoria == indiceCancionActual)

        indiceCancionActual = cancionAleatoria
        actualizarCancionYReproducir(context)
    }

    private fun SiguienteCancionLista(context: Context) {
        indiceCancionActual = if (indiceCancionActual < listaDeCanciones.size - 1) {
            indiceCancionActual + 1
        } else {
            0
        }
        actualizarCancionYReproducir(context)
    }

    private fun actualizarCancionYReproducir(context: Context) {
        _cancionActual.value = listaDeCanciones[indiceCancionActual]
        _exoPlayer.value?.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _cancionActual.value.cancion)))
        _exoPlayer.value?.prepare()
        _exoPlayer.value?.playWhenReady = true
    }

    fun movimientoSlider(siguienteSector: Int) {
        _exoPlayer.value?.seekTo(siguienteSector.toLong())
    }

    fun pulsadoBotonRepetir() {
        _repetirCancion.value = !_repetirCancion.value
    }

    fun pulsadoBotonRandom() {
        _cancionRandom.value = !_cancionRandom.value
    }

    fun retrocederCancion(context: Context) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        indiceCancionActual = if (indiceCancionActual > 0) {
            indiceCancionActual - 1
        } else {
            listaDeCanciones.size - 1
        }
        actualizarCancionYReproducir(context)
    }

    @Throws(Resources.NotFoundException::class)
    fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
        val res: Resources = context.resources
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + res.getResourcePackageName(resId)
                    + '/' + res.getResourceTypeName(resId)
                    + '/' + res.getResourceEntryName(resId)
        )
    }
}