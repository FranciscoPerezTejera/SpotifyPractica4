package com.example.spotifypractica4.reproductor

import android.widget.Spinner
import android.widget.SpinnerAdapter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotifypractica4.R

@Composable
fun PantallaReproductor() {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween){
        Text(text = "Album o título del disco", modifier = Modifier.fillMaxWidth().padding(8.dp))
        Image(painter = painterResource(id = R.drawable.badbunny), contentDescription = "",
            modifier = Modifier.fillMaxWidth().padding(8.dp).weight(1f))
        Text(text = "Título de la canción", modifier = Modifier.fillMaxWidth().padding(8.dp))
        Slider(value = 0f, onValueChange = {})
        Row (modifier = Modifier.fillMaxWidth().padding(8.dp).weight(1f)) {
            Button(onClick = { /*TODO*/ }) {}
            Button(onClick = { /*TODO*/ }) {}
            Button(onClick = { /*TODO*/ }) {}
            Button(onClick = { /*TODO*/ }) {}
            Button(onClick = { /*TODO*/ }) {}
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ReproductorPreview() {

    PantallaReproductor()

}