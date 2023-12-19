package com.example.spotifypractica4.reproductor


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
        Text(text = "Album o título del disco", modifier = Modifier
            .align(Alignment.CenterHorizontally))
        Image(painter = painterResource(id = R.drawable.badbunny), contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .size(450.dp))
        Text(text = "Título de la canción", modifier = Modifier
            .align(Alignment.CenterHorizontally))
        Slider(value = 0f, onValueChange = {})
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp)){
            Text(text = "0:00")
            Text(text = "3:41")
        }
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {

            Button(onClick = { /*TODO*/ }) {
            }
            Button(onClick = { /*TODO*/ }) {
            }
            Button(onClick = { /*TODO*/ }) {
            }
            Button(onClick = { /*TODO*/ }) {
            }
            Button(onClick = { /*TODO*/ }) {
            }
        }
    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ReproductorPreview() {

    PantallaReproductor()

}