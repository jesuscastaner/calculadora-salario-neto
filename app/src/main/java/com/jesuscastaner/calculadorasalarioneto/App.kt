package com.jesuscastaner.calculadorasalarioneto

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/**
 * Inicializa la app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // crear una ventana que sirva como contenedor para los demas componentes de las vistas
    Scaffold(
        // franja superior
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = "Calculadora de salario neto",
                        fontSize = 22.sp,
                    )
                }
            )
        },

        // franja inferior
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Alumno: Jesús Castañer Moreno",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Programación Multimedia y Dispositivos Móviles",
                        fontSize = 14.sp,
                    )

                }


            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(paddingValues = innerPadding),
        ) {
            // inicializa el navegador
            Navegador()
        }
    }
}
