package com.jesuscastaner.calculadorasalarioneto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Vista donde se muestran los resultados del calculo.
 */
@Composable
fun VistaResultados(
    salarioBruto: Double,
    ss: Double,
    irpf: Double,
    salarioNeto: Double,
    volverAlFormulario: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 60.dp,
                vertical = 30.dp
            )
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 20.dp)
    ) {
        // cabecera
        Cabecera(
            titulo = "Resultados",
            descripcion = "Estos son tus resultados:"
        )

        // resultados
        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 30.dp
                ),
                verticalArrangement = Arrangement.spacedBy(space = 20.dp)
            ) {
                Text(text = "Salario bruto: ${"%.2f".format(salarioBruto)} €")
                Text(text = "Seguridad Social: ${"%.2f".format(ss)} €")
                Text(text = "IRPF: ${"%.2f".format(irpf)} €")
                Text(
                    text = "Salario neto: ${"%.2f".format(salarioNeto)} €",
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        // boton para volver a la vista del formulario
        Button(
            onClick = { volverAlFormulario() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver")
        }
    }
}
