package com.example.sqlite

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sqlite.ui.theme.SqliteTheme
import  androidx.compose.runtime.getValue
import  androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.core.text.isDigitsOnly

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           PantallaAlumnos()
        }
    }
}

@Composable
fun PantallaAlumnos(){

    val context = LocalContext.current

    val db = remember { DatabaseHelper(context) }

    var nombre by remember { mutableStateOf("") }

    var edad by remember { mutableStateOf("") }

    var alumnos by remember { mutableStateOf(db.obtenerAlumnos()) }

    Column(
        modifier = Modifier .fillMaxSize() .padding(20.dp)
    ) {
        Text(
            text = "Registro de alumnos",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier .height(20.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
            },

            label = {
                Text("Nombre:")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier .height(10.dp)
        )

        OutlinedTextField(
            value = edad,
            onValueChange = {
                edad = it
            },
            label = {
                Text("Edad")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier .height(10.dp)
        )

        Button(
            onClick = {
                if (
                    nombre.isNotBlank() &&
                    edad.isNotBlank() &&
                    edad.isDigitsOnly()
                ) {
                    db.insertarAlumno(
                        nombre,
                        edad.toInt()
                    )

                    alumnos = db.obtenerAlumnos()

                    nombre =""
                    edad = ""
                }
            },

            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Guardar alumno")
        }

        Spacer(
            modifier = Modifier .height(10.dp)
        )

        Text(
            text = "Alumno registrados:",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier .height(10.dp)
        )

        LazyColumn{
            items(alumnos) { alumno ->

                Card(
                    modifier = Modifier .fillMaxWidth()
                        .padding(vertical = 5.dp)
                )
                {
                    Column(
                        modifier = Modifier .padding(15.dp)
                    ) {
                        Text(
                            text = alumno.nombre,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Edad ${alumno.edad}"
                        )

                        Text(
                            text = "ID: ${alumno.id}"
                        )

                    }
                }

            }
        }
    }
}


